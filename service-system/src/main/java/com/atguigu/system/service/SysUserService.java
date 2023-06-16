package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/10/2023 7:58 PM
 */
public interface SysUserService extends IService<SysUser> {
    Page<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    //首先根据用户名去数据库中查询用户
    SysUser getSysUserByUserName(String username);

    //获取用户的信息(用户信息,菜单信息,对应的按钮权限)
    Map<String, Object> getUserInfoByUserId(Long id);

    //根据用户的id获取按钮权限
    List<String> getBtnPermissionByUserId(Long id);
}
