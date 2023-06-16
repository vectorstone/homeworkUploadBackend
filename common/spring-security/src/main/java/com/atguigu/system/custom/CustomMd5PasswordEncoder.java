package com.atguigu.system.custom;

import com.atguigu.common.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 密码处理
 * </p>
 *
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
        return encodedPassword.equals(encode(rawPassword));
    }
}