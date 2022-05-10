package it.luca.project.restaurant.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.luca.project.restaurant.controller.dto.GenericResponse;
import it.luca.project.restaurant.controller.dto.RecoveryPasswordDto;
import it.luca.project.restaurant.controller.dto.UserRegistrationDto;
import it.luca.project.restaurant.entity.User.User;
import it.luca.project.restaurant.exception.RequiredFieldException;
import it.luca.project.restaurant.exception.UnauthorizedException;
import it.luca.project.restaurant.exception.UserAlreadyExistException;
import it.luca.project.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

@RestController
@RequestMapping("api/rest/auth")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Value("${config.path.auth}")
    private String authBasePath;


    @PostMapping("/create/account")
    @ApiOperation("This Api is for register new User")
    @ApiResponses(
            value = {
                    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Account created succesfully"),
                    @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "You're not authorized"),
                    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server errror")
            }
    )
    public ResponseEntity<GenericResponse<?>> createNewAccount(@RequestBody UserRegistrationDto newUser) throws UserAlreadyExistException, RequiredFieldException, MessagingException, UnsupportedEncodingException {

        userService.checkIfUserExist(newUser.getUsername());

        User u = userService.buildUser(newUser);

        userService.saveUser(u);

        try {
            sendVerificationEmail(u,authBasePath);
        }catch (Exception e){

            return  ResponseEntity.internalServerError().body(new GenericResponse<>("Error sendign mail try again",null));
        }

        GenericResponse resp = buildGeneriResponse(null,"Account succesfully created");

        return ResponseEntity.ok(resp);

    }

    @GetMapping("/verify")
    @ApiOperation("This Api is for verify new User")
    @ApiResponses(
            value = {
                    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Account created verified"),
                    @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "You're not authorized"),
                    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server errror")
            }
    )
    public ResponseEntity<GenericResponse<?>> verifyAccount(@Param("code") String code) throws UnauthorizedException {


        User user = userService.verifyUserByCode(code);

        if(user.getVerificationCode().equals(code)) {
            user.setEnabled(true);
            userService.saveUser(user);
        }
        else
            throw new UnauthorizedException("You're code is not valid");

        GenericResponse response = buildGeneriResponse(null,"Account succesfully created");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/recovery/account/{username}")
    @ApiOperation("This api is for recover the account")
    @ApiResponses(
            value = {
                    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Account succesfully recovered"),
                    @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "You're not authorized"),
                    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server errror")
            }
    )
    public ResponseEntity<GenericResponse<?>> sendRecoveryLink(@PathVariable("username") String username){

        User u = userService.findByUsername(username);

        try {
            userService.createRecoveryUuid(u);

            sendRecoveryToken(u,authBasePath);
        }catch (Exception e){

            return  ResponseEntity.internalServerError().body(new GenericResponse<>("Error sendign mail try again",null));
        }

        GenericResponse response = buildGeneriResponse(null,"Recovery mail succesfully sended");

        return ResponseEntity.ok(response);


    }

    @PostMapping("/change/password")
    @ApiOperation("This api is for change the password")
    @ApiResponses(
            value = {
                    @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Password succesfully recovered"),
                    @ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "You're not authorized"),
                    @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server errror")
            }
    )
    public ResponseEntity<GenericResponse<?>> changePassword(@RequestBody RecoveryPasswordDto recoveryPassword) throws RequiredFieldException {

        User u = userService.findByIdAndUuid(recoveryPassword.getId(),recoveryPassword.getUuid());

        if(recoveryPassword.getNewPassword() == null || recoveryPassword.getNewPassword().isEmpty())
            throw new RequiredFieldException("New password is mandatory");


        userService.changePassword(u,recoveryPassword.getNewPassword());

        GenericResponse response = buildGeneriResponse(null,"Password succesfully changed");

        return ResponseEntity.ok(response);

    }




    private <T> GenericResponse<T> buildGeneriResponse(T body,String message){
        GenericResponse<T> resp = new GenericResponse<>();
        resp.setObject(body);
        resp.setMessage(message);
        return  resp;
    }

    private void sendRecoveryToken(User user,String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "laduluca95@hotmail.it";
        String senderName = "Barber";
        String subject = "Please verify your account";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to recover your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/recovery?code=" + user.getRecoveryUuid()+"?accountId="+user.getId();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "laduluca95@hotmail.it";
        String senderName = "Barber";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
}
