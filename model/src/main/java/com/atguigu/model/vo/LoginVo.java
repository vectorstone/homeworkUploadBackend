package com.atguigu.model.vo;


import java.io.Serializable;

/**
 * 登录对象
 */
public class LoginVo implements Serializable {

    /**
     * 手机号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
