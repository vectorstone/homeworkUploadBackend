package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/6/2023 4:54 PM
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public Page<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        return sysRoleMapper.selectPage(pageParam,sysRoleQueryVo);
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        //获取所有的角色列表
        List<SysRole> allRoles = sysRoleMapper.selectList(null);

        //获取该用户当前的角色id(使用QueryWrapper根据用户的id来进行查询)
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);
        //此时获取的知识用户所拥有的角色的list,我们还需要进一步的将角色的id提取出来
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            userRoleIds.add(sysUserRole.getRoleId());
        }

        //将上面获取到的两个结果放到map集合中并返回给controller
        Map<String,Object> map = new HashMap<>();
        map.put("allRoles",allRoles);
        map.put("userRoleIds",userRoleIds);

        return map;
    }

    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //1.根据用户的id,删除用户的现有角色信息
        //下面的这个方法有点坑,竟然删除的是主键的id,不是按照user_id来删除的
        //UPDATE sys_user_role SET is_deleted=1 WHERE id=? AND is_deleted=0
        //sysUserRoleMapper.deleteById(assignRoleVo.getUserId());
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assignRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);

        //2.根据用户的角色id列表,更新用户的角色信息
        //2.1获取用户新的角色id列表
        List<Long> roleIdList = assignRoleVo.getRoleIdList();
        //2.2遍历循环用户新的角色id列表
        for (Long roleId : roleIdList) {
            //2.3创建新的用户角色对象
            SysUserRole sysUserRole = new SysUserRole();
            //2.4将角色id设置到用户角色对象中
            sysUserRole.setRoleId(roleId);
            //2.5将用户id设置到用户角色对象中
            sysUserRole.setUserId(assignRoleVo.getUserId());
            //2.6将上述新创建的用户角色对象插入到数据库中
            sysUserRoleMapper.insert(sysUserRole);
        }
    }
}
