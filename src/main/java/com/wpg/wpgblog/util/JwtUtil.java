package com.wpg.wpgblog.util;

import cn.hutool.core.util.StrUtil;
import com.wpg.wpgblog.common.BaseException;
import com.wpg.wpgblog.common.Consts;
import com.wpg.wpgblog.common.Status;
import com.wpg.wpgblog.exception.MyException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class JwtUtil {
    private static final String JWT_KEY = "wpgblog";

    //10分钟
    private static final Long TTL = 600000L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String createJWT(String email, String subject) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(email)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, JWT_KEY)
                ;

        String jwt = builder.compact();

        stringRedisTemplate.opsForValue()
                .set(Consts.REDIS_JWT_KEY_PREFIX + subject, jwt, TTL, TimeUnit.MILLISECONDS);
        return jwt;
    }

    public Claims parseJWT(String jwt) {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(jwt)
                    .getBody();
        String username = claims.getSubject();
        String redisKey = Consts.REDIS_JWT_KEY_PREFIX + username;

        // 校验redis中的JWT是否存在
        Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
        if (Objects.isNull(expire) || expire <= 0) {
            throw new MyException(Status.TOKEN_EXPIRED);
        }
        return claims;
    }

    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        stringRedisTemplate.delete(Consts.REDIS_JWT_KEY_PREFIX + username);
    }

    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token;
    }

}
