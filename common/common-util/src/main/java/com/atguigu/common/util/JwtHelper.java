package com.atguigu.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import java.util.Date;
/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/13/2023 7:14 PM
 * 用来生成json web令牌的工具类
 */
public class JwtHelper {
    //token过期时间,超过过期时间就无法从jwt中取到token了
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密密钥
    private static String tokenSingKey = "12345";

    //根据用户id和用户名称生成token字符串
    public static String createToken(Long userId,String username){
        String token = Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //可以根据实际的情况来进行生成密钥的选项
                .claim("userId",userId)
                .claim("username",username)
                //根据密钥来进行加密
                .signWith(SignatureAlgorithm.HS512,tokenSingKey)
                //将密钥进行压缩
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static Long getUserId(String token){
        try{
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSingKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token){
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSingKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void removeToken(String token){
        //jwttoken无需删除,客户端扔掉即可
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L,"admin");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}
