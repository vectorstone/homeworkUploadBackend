package com.atguigu.system;

import com.atguigu.common.util.MD5;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/6/2023 3:59 PM
 */
// @SpringBootTest
public class FunctionTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    //查询所有
    @Test
    public void test1(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }
    //使用QueryWrapper来查询符合指定条件的对象
    @Test
    public void test2(){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name","系统管理员").eq("role_code","vip");
        // queryWrapper.eq("role_name","系统管理员").or().eq("role_code","vip");
        sysRoleMapper.selectList(queryWrapper).forEach(System.out::println);
    }
    //增加SysRole
    @Test
    public void test3(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("超级管理员");
        sysRole.setRoleCode("vip");
        sysRole.setDescription("这是一个超级管理员");
        int insert = sysRoleMapper.insert(sysRole);
    }
    //修改
    @Test
    public void test4(){
        //先查询出要修改的这个对象
        SysRole sysRole = sysRoleMapper.selectById(9);
        sysRole.setRoleName("裘千尺");
        //一定要将updateTime设置为null,因为mysql数据库会自动生成时间
        sysRole.setUpdateTime(null);
        //将该对象丢给修改的方法来验证修改是否ok
        int i = sysRoleMapper.updateById(sysRole);
    }
    //删除
    @Test
    public void test5(){
        int i = sysRoleMapper.deleteById(9);
    }
    //测试一下mybatis plus里面的serverImpl好不好用
    @Test
    public void test6(){
        sysRoleService.list().forEach(System.out::println);
    }
    @Test
    public void test7(){
        //UPDATE sys_user_role SET is_deleted=1 WHERE id=? AND is_deleted=0
        // sysUserRoleMapper.deleteById(2);
        String encrypt = MD5.encrypt("fengge666");
        System.out.println("encrypt = " + encrypt);
    }
    @Test
    void test8(){
        //判断字符串里面是否包含中文的测试
        //有的人不删除提示的那个内容,所以增加一个判断头像的链接中是否包含中文
        String str = "sdfsdfsf8w3!@2323,.,mdfdf";
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);

        if (m.find()){
            System.out.println("包含中文");
        }
    }
}
