package com.atguigu.system;

import com.atguigu.common.util.JwtHelper;
import com.atguigu.model.system.Student;
import com.atguigu.model.system.SysMenu;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/12/2023 9:17 AM
 */
@SpringBootTest
public class BuildTree {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;
    //测试获取sysMenu对象数据
    @Test
    public void test1(){
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);
        sysMenus.forEach(System.out::println);
    }
    //构建tree的测试
    @Test
    public void buildTree(){
        List<SysMenu> sysMenus = sysMenuMapper.selectList(null);

        List<SysMenu> trees = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            if(sysMenu.getParentId() == 0){
                /*if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }*/
                trees.add(findChildren(sysMenu, sysMenus));
            }
        }
        for (SysMenu tree : trees) {
            System.out.println(tree);
        }

    }
    public static SysMenu findChildren(SysMenu sysMenu,List<SysMenu> list){
        for (SysMenu menu : list) {
            if(sysMenu.getId() == menu.getParentId()){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(menu,list));
            }
        }
        return sysMenu;
    }
    @Test
    public void test2(){
        Student student = new Student(22,"女");

    }
    @Test
    public void test3(){
        List<SysMenu> nodes = sysMenuService.findNodes();
        nodes.forEach(System.out::println);
    }
    @Test
    public void test4(){
        String token = JwtHelper.createToken(1L,"admin");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}
