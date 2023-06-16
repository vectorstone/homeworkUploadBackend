package com.atguigu.system.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.atguigu.system.exception.GuiguException;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysUser;
import com.atguigu.system.custom.CustomUser;
import com.atguigu.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/14/2023 7:54 PM
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名获取sysUser对象
        SysUser sysUser = sysUserService.getSysUserByUserName(s);

        if(sysUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        if(sysUser.getStatus() == 0){
            throw new RuntimeException("帐号已停用");
        }

        //获取当前的这个用户的权限集合
        List<String> userPermsList = sysUserService.getBtnPermissionByUserId(sysUser.getId());
        sysUser.setUserPermsList(userPermsList);
        //这个地方为什么还是返回一个空集合,按道理应该把刚刚查出来的按钮的权限的集合放进去,先试试再说
        return new CustomUser(sysUser,Collections.emptyList());
    }
}
