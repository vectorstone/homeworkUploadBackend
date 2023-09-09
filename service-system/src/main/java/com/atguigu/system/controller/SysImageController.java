package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.Homework;
import com.atguigu.model.system.SysImages;
import com.atguigu.system.mapper.HomeworkMapper;
import com.atguigu.system.mapper.UserImagesMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/9/2023 11:26 AM
 */
@RestController
@RequestMapping("/admin/system/image")
@Api(tags = "作业图片查询的模块")
public class SysImageController {
    @Autowired
    UserImagesMapper userImagesMapper;
    @Autowired
    HomeworkMapper homeworkMapper;
    @ApiOperation("根据作业的名称来查询对应的图片的list集合")
    @GetMapping
    public Result queryImagesByName(@RequestParam("name")String name){
        List<SysImages> imagesList = userImagesMapper.selectList(Wrappers.lambdaUpdate(SysImages.class).eq(SysImages::getImageName, name));
        return Result.ok(imagesList);
    }
    @ApiOperation("查询最近的一次作业的所有的图片")
    @GetMapping("/latest")
    public Result queryLatestImages(){
        //这个接口的作用就是一进入页面的时候就查询最近的作业的图片的集合
        //先将所有的作业的集合查询出来
        List<Homework> homeworkList = homeworkMapper.selectList(null);
        //获取最近的一个作业对象
        Homework homework = homeworkList.get(homeworkList.size() - 1);
        //然后查询该作业下面的所有的图片的集合
        List<SysImages> imagesList = userImagesMapper.selectList(Wrappers.lambdaUpdate(SysImages.class).eq(SysImages::getImageName, homework.getName()));
        //还需要将最近的一次的作业的名称也传回去
        Map<String,Object> map = new HashMap<>();
        map.put("imagesList",imagesList);
        map.put("homeworkName",homework.getName());
        return Result.ok(map);
    }

    @ApiOperation("查询所有的作业的名称的集合")
    @GetMapping("/homework/name")
    public Result queryHomework(){
        List<Homework> homeworkList = homeworkMapper.selectList(null);
        return Result.ok(homeworkList);
    }
    @ApiOperation("查询所有的图片")
    @GetMapping("/list/{pageNum}/{pageSize}")
    public Result queryAllImages(@PathVariable("pageNum")Integer pageNum,
                                 @PathVariable("pageSize")Integer pageSize  ){
        Page<SysImages> page = new Page<>(pageNum,pageSize);
        Page<SysImages> sysImagesPage = userImagesMapper.selectPage(page, null);
        return Result.ok(sysImagesPage);
    }

}
