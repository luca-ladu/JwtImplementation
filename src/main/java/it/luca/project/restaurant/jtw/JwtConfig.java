package it.luca.project.restaurant.jtw;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "application.jwt")
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfig {

    private String tokenPrefix;
    private String secretKey;
    private Integer tokenExpirationAfterDays;



    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
