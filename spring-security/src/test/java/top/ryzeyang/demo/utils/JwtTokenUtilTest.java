package top.ryzeyang.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ken
 * @date 2020/11/27 下午 03:20
 */
class JwtTokenUtilTest {
   /* @Test
    void generateKey() {
        *//**
         * SECRET 是签名密钥，只生成一次即可，生成方法：
         * Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
         * String secretString = Encoders.BASE64.encode(key.getEncoded()); # 本文使用 BASE64 编码
         * *//*
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);
//        Blk1X8JlN4XH4s+Kuc0YUFXv+feyTgVUMycSiKbiL0YhRddy872mCNZBGZIb57Jn2V1RtaFXIxs8TvNPsnG//g==
    }*/

   /* @Test
     void getClaimsFromToken(String token) {
        SecretKey secretKey = getSecretKey();
        Claims claims  = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims);
    }*/
/*
    @Test
    void generateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "给你一株四叶草");
        claims.put("msg", "欢迎关注博主的公众号 给你一株四叶草");
        String s = new JwtTokenUtil().generateToken2(claims);
        System.out.println(s);
    }*/
}