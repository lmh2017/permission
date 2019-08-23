package com.defu.permission.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/5/13
 * time: 16:53
 */
@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${jwt.name:token}")
    private String tokenHeaderName;

    private final static long ux = 1000;

    public String createToken(String comment){
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        String token = JWT.create()
                .withIssuer(jwtProperties.getName())
                .withSubject(comment)
                .withExpiresAt(new Date((new Date().getTime()+jwtProperties.getExpire()*ux)))
                .sign(algorithm);
        return token;

    }

    public boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtProperties.getName())
                    .build(); //Reusable verifier instance
            verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            log.error(exception.getMessage()+"token校验失败");
            return false;
        }
        return true;
    }

    public String decodeToken(String token){
        return JWT.decode(token).getSubject();
    }

    /**
     * 获取用户token
     * @return
     */
    public String getUserToken() {
        HttpServletRequest httpServletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return httpServletRequest.getHeader(tokenHeaderName);
    }
}
