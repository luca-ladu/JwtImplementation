package it.luca.project.restaurant.jtw;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;

public class JwtUsernameAndPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    //This class is for verify the credential

    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;

    public JwtUsernameAndPasswordAuthFilter(AuthenticationManager authenticationManager,JwtConfig jwtConfig,SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //Validate credential
        //The first thing is to grab the usern an password

        //Read the value from the request
        try {
            UsernameAndPasswordAuthRequest andPasswordAuthRequest=
                    new ObjectMapper().readValue(request.getInputStream(),UsernameAndPasswordAuthRequest.class);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(andPasswordAuthRequest.getUsername(),andPasswordAuthRequest.getPassword());
            //We make sure that username exist and check if the password is good
            Authentication authenticate = authenticationManager.authenticate(authentication);

            return  authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //Will be invoked after the attempt auth is succefully
        //Inside this method we will create the token to send at the client


        String jwt = Jwts.builder()
                .setSubject(authResult.getName())
                        .claim("authorities",authResult.getAuthorities())
                                .setIssuedAt(new java.util.Date())
                                        .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                                                .signWith(secretKey)
                                                        .compact();
        //Now we have the token
        //We now add the token to the response

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix()+jwt);
    }
}
