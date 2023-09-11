package com.atguigu.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.common.util.BusinessException;
import com.atguigu.common.util.ResponseEnum;
import com.atguigu.model.system.Homework;
import com.atguigu.model.system.SysImages;
import com.atguigu.model.vo.Classmates;
import com.atguigu.model.vo.ClassmatesExcel;
import com.atguigu.model.vo.SubmitStatus;
import com.atguigu.system.listener.ClassmatesDataListener;
import com.atguigu.system.mapper.ClassmatesMapper;
import com.atguigu.system.mapper.HomeworkMapper;
import com.atguigu.system.mapper.UserImagesMapper;
import com.atguigu.system.service.ClassmatesService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/10/2023 1:39 PM
 */
@Service
public class ClassmatesServiceImpl extends ServiceImpl<ClassmatesMapper, Classmates> implements ClassmatesService {

    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    UserImagesMapper userImagesMapper;

    //这个方法只使用了一次,没必要使用那么多的校验的什么规则了
    @Override
    public void importExcel(MultipartFile file) {
        // 校验判断文件是否合规(文件必须存在,后缀,文件的大小)
        // 判断文件的后缀是否合规
        boolean flag = file.getOriginalFilename().toLowerCase().endsWith(".xls") ||
                file.getOriginalFilename().toLowerCase().endsWith(".xlsx") ||
                file.getOriginalFilename().toLowerCase().endsWith(".cvs");
        /* Asserts.AssertTrue( flag, ResponseEnum.UPLOAD_ERROR );
        // 判断文件的大小不可以为0 断言文件的大小必须 > 0 ,如果小于0,立马抛异常
        Asserts.AssertTrue(file.getSize() > 0, ResponseEnum.UPLOAD_ERROR);
        // 判断文件必须存在
        Asserts.AssertNotNull(file, ResponseEnum.DATA_NULL_ERROR); */


        // 文件上传的核心业务代码
        try {
            // 使用MultipartFile的输入流来读取文件
            EasyExcel.read(file.getInputStream())
                    .head(ClassmatesExcel.class)
                    .sheet(0)
                    .registerReadListener(new ClassmatesDataListener(this))
                    .doRead();
        } catch (Exception e) { // 放大异常的类型
            // 将异常的信息记录到日志文件中
            log.error("出异常了,异常信息为:{}" + ExceptionUtils.getStackTrace(e));
            // 抛出我们自定义的异常
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR);
        }
    }

    @Override
    public List<SubmitStatus> querySubmitStatus(List<String> homeworkList) {
        //朱洪峰肄业,不查询他的数据
        List<Classmates> classmateslist = this.list(Wrappers.lambdaQuery(Classmates.class)
                .notIn(Classmates::getName, "朱洪峰"));
// List<SysImages> imagesList = userImagesMapper.selectList(null);

//只查询当天的作业,添加到下面的这个集合中的作业都是不会查询的 ,TODO 这个值可以通过前端里面传过来
        /* List<String> outOfDateHomework = new ArrayList<>();
        outOfDateHomework.add("电商架构图");
        outOfDateHomework.add("电商表关系图");
        outOfDateHomework.add("分布式加锁解锁流程图");
        outOfDateHomework.add("缓存数据的访问流程"); */

        List<Homework> homework = homeworkMapper.selectList(Wrappers.lambdaQuery(Homework.class)
                // .notIn(Homework::getName, homeworkList.stream().map(Homework::getName).collect(Collectors.toList())));
                // .notIn(Homework::getName, homeworkList));//这个方式在前端里面将会查询不勾选的作业的提交的情况,有点反人类
                .in(Homework::getName, homeworkList));//修改成,只查询勾选的作业的提交的情况

// List<SysUser> sysUsers = sysUserMapper.selectList(null);
        Map<String, String> map = new HashMap<>();

//怎么遍历呢
        classmateslist.forEach(classmates -> {
            String username = classmates.getName();
            homework.forEach(h -> {
                String homeworkName = h.getName();
                Integer count = userImagesMapper.selectCount(Wrappers.lambdaUpdate(SysImages.class).eq(SysImages::getUsername, username)
                        .eq(SysImages::getImageName, homeworkName));
                if (count == 0) {
                    //如果查询不到结果那么就是没有交作业
                    boolean b = map.containsKey(username);
                    if (b) {
                        //进来这里面说明之前的有其他的作业没交,直接追加
                        String s = map.get(username);
                        map.put(username, s + " , " + homeworkName);
                    } else {
                        //第一次查询到他有作业没交
                        map.put(username, homeworkName);
                    }
                }
            });
        });
        List<SubmitStatus> submitStatuses = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            // System.out.println(entry.getKey() + " : " +entry.getValue());
            submitStatuses.add(new SubmitStatus(entry.getKey(), entry.getValue()));
        }
        return submitStatuses;
    }
}
