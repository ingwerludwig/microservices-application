package com.javagrind.authservice.security.services;

import com.javagrind.authservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final JwtUtils jwtUtils;
    @Value("{redis.host")
    private String redisHost;

    private final Integer redisPort=6379;
    JedisPool jedisPool = new JedisPool(redisHost, redisPort);

    public void store(String token){
        try (Jedis jedis = jedisPool.getResource()) {
            String email = jwtUtils.getUserNameFromJwtToken(token);
            jedis.set(email, token);
        } catch (JedisException e) {
            throw new JedisException(e.getMessage());
        }
    }

    public Boolean isValid(String email){
        try (Jedis jedis = jedisPool.getResource()) {
            String token = jedis.get(email);

            if (token != null) {
                Long expDate = jwtUtils.getExpiredAt(token).getTime();
                Long dateNow = (System.currentTimeMillis() / 1000);
                System.err.println("expDate : " + expDate);
                System.err.println("dateNow : " + dateNow);

                if ((expDate > dateNow) && jwtUtils.validateJwtToken(token))
                    return Boolean.TRUE;
            }

        } catch (JedisException e) {
            throw new JedisException(e.getMessage());
        }
        return Boolean.FALSE;
    }

    public String isThere(String email){
        try (Jedis jedis = jedisPool.getResource()) {
            String token = jedis.get(email);

            if (token != null) return token;
        } catch (JedisException e) {
            throw new JedisException(e.getMessage());
        }
        return null;
    }

    public void destroyToken(String email){
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(email);

        } catch (JedisException e) {
            throw new JedisException(e.getMessage());
        }
    }

}