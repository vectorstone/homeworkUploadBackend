package com.atguigu.system;

import com.atguigu.model.vo.Classmates;
import com.atguigu.model.system.Homework;
import com.atguigu.model.system.SysImages;
import com.atguigu.model.system.SysUser;
import com.atguigu.system.mapper.HomeworkMapper;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.mapper.UserImagesMapper;
import com.atguigu.system.service.ClassmatesService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ExcelStatisticApplicationTests {
    @Autowired
    ClassmatesService classmatesService;
    @Autowired
    UserImagesMapper userImagesMapper;
    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Test
    void contextLoads() {
        //朱洪峰肄业,不查询他的数据
        List<Classmates> classmateslist = classmatesService.list(Wrappers.lambdaQuery(Classmates.class)
                .notIn(Classmates::getName,"朱洪峰"));
        // List<SysImages> imagesList = userImagesMapper.selectList(null);

        //只查询当天的作业,添加到下面的这个集合中的作业都是不会查询的
        List<String> outOfDateHomework = new ArrayList<>();
        outOfDateHomework.add("电商架构图");
        outOfDateHomework.add("电商表关系图");
        outOfDateHomework.add("分布式加锁解锁流程图");
        outOfDateHomework.add("缓存数据的访问流程");
        List<Homework> homework = homeworkMapper.selectList(Wrappers.lambdaQuery(Homework.class)
                .notIn(Homework::getName,outOfDateHomework));

        // List<SysUser> sysUsers = sysUserMapper.selectList(null);
        Map<String, String> map = new HashMap<>();
        //怎么遍历呢
        classmateslist.forEach(classmates -> {
            String username = classmates.getName();
            homework.forEach(h -> {
                String homeworkName = h.getName();
                Integer count = userImagesMapper.selectCount(Wrappers.lambdaUpdate(SysImages.class).eq(SysImages::getUsername, username)
                        .eq(SysImages::getImageName,homeworkName));
                if (count == 0){
                    //如果查询不到结果那么就是没有交作业
                    boolean b = map.containsKey(username);
                    if(b){
                        //进来这里面说明之前的有其他的作业没交
                        String s = map.get(username);
                        map.put(username,s + " , "+ homeworkName);
                    }else{
                        //第一次查询到他有作业没交
                        map.put(username,homeworkName);
                    }
                }
            });
        });
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + " : " +entry.getValue());
        }
    }
    @Test
    void test2(){
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        List<Classmates> classmateslist = classmatesService.list();
        List<String> classmates = classmateslist.stream().map(Classmates::getName).collect(Collectors.toList());
        List<String> registryUsers = sysUsers.stream().map(SysUser::getName).collect(Collectors.toList());
        sysUsers.forEach(user -> {
            if (!classmates.contains(user.getName())){
                System.out.println(user.getName());
            }
        });
    }
}
