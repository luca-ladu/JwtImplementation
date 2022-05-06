package it.luca.project.restaurant.jtw;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {


    private final  JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtTokenVerifier(JwtConfig jwtConfig,SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        String authHeader = httpServletRequest.getHeader(jwtConfig.getAuthorizationHeader());

        if(Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(jwtConfig.getTokenPrefix())){
            //Request will be rejected
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        try {
            String token = authHeader.replace(jwtConfig.getTokenPrefix(),"");
            Jws<Claims> claimsJwt = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = claimsJwt.getBody();

            String username = body.getSubject();
            List<Map<String,String>> authorities = (List<Map<String,String>>)body.get("authorities");

            List<SimpleGrantedAuthority> simpleGrantedAuthorities =
            authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toList());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);


        }catch (JwtException e){

            throw new IllegalStateException("Token cannot be trusted");
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);


    }
}
