package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.vo.AssignMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findNodes();

    void removeMenuById(Long id);

    //根据角色获取菜单
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    //给角色分配菜单
    void doAssign(AssignMenuVo assignMenuVo);


}
