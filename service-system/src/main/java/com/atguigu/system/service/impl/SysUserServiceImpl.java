package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.helper.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/10/2023 7:59 PM
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    public Page<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo){
        return sysUserMapper.selectPage(pageParam,sysUserQueryVo);
    }

    //和登录的controller里面用来进行登录校验的方法相关
    @Override
    public SysUser getSysUserByUserName(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    //获取用户的信息(用户信息,菜单信息,对应的按钮权限)
    @Override
    public Map<String, Object> getUserInfoByUserId(Long userId) {
        //1.先创建一个map集合用来一会给controller返回map集合
        Map<String,Object> userInfoMap = new HashMap<>();

        //2.查询用户信息,并设置到map集合里面
        SysUser sysUser = sysUserMapper.selectById(userId);
        //2.1将用户的信息放进去
        userInfoMap.put("name",sysUser.getName());
        //2.2将用户的头像信息放进去
        userInfoMap.put("avatar",sysUser.getHeadUrl());
        //2.3将用户的角色信息放进去(因为还没有涉及到权限的事情,所以这里先放一个假的数据进去)
        userInfoMap.put("roles","[admin]");

        //3.查询用户的菜单信息(需要判断是不是管理员,如果是管理员的话直接查询所有,如果不是管理员的话需要进行多表查询)
        List<SysMenu> menuByUserId = getMenuByUserId(userId);

        //将获取到的用户的菜单构建成树形结构
        List<SysMenu> sysMenuList = MenuHelper.buildTree(menuByUserId);

        //将构建成树形的menuList再构建成前端所需要的router的结构
        List<RouterVo> routerVos = RouterHelper.buildRouters(sysMenuList);
        userInfoMap.put("routers",routerVos);

        //4.获取用户的按钮的信息
        List<String> btnPermissionByUserId = getBtnPermissionByUserId(userId);
        userInfoMap.put("buttons",btnPermissionByUserId);

        return userInfoMap;
    }

    //根据用户的id获取对应的菜单
    private List<SysMenu> getMenuByUserId(Long userId){
        //3.查询用户的菜单信息(需要判断是不是管理员,如果是管理员的话直接查询所有,如果不是管理员的话需要进行多表查询)
        //这里默认管理员的userId是1
        List<SysMenu> sysMenuList = new ArrayList<>();
        if(userId == 1){
            //进来里面说明是管理员,status得是1,还要排序
            QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
            sysMenuQueryWrapper.eq("status",1);
            sysMenuQueryWrapper.eq("is_deleted",0);
            sysMenuQueryWrapper.orderByAsc("sort_value");
            sysMenuList = sysMenuMapper.selectList(sysMenuQueryWrapper);
        }else{
            //进来这里面说明不是管理员
            sysMenuList = sysMenuMapper.getMenuByUserId(userId);
        }
        //将查询到的menuList构建成树形的结构,并返回,不能在这里构建成true,否则后面的btnPerm只能获取到一个list,查不到数据
        // return MenuHelper.buildTree(sysMenuList);
        return sysMenuList;
    }

    //根据用户的id获取对应的按钮的权限
    @Override
    public List<String> getBtnPermissionByUserId(Long userId) {
        //根据用户id获取对应的菜单的选项
        List<SysMenu> menuByUserId = getMenuByUserId(userId);
        //遍历得到对应的按钮的权限
        /*List<String> btnPermission = new ArrayList<>();
        menuByUserId.forEach(menu -> {
            if(menu.getType() == 2){
                btnPermission.add(menu.getPerms());
            }
        });*/
        //上面的那块代码的高级写法
        List<String> btnPermission = menuByUserId.stream().filter(menu -> menu.getType() == 2).map(SysMenu::getPerms).collect(Collectors.toList());

        return btnPermission;
    }
}
