package com.atguigu.system.controller;


import com.atguigu.common.result.Result;
import com.atguigu.model.system.Homework;
import com.atguigu.model.vo.SubmitStatus;
import com.atguigu.system.service.ClassmatesService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/10/2023 11:46 AM
 */
@RestController
@Api(tags = "同学作业提交情况查看模块")
@RequestMapping("/admin/system")
public class ClassmatesController {

    @Autowired
    ClassmatesService classmatesService;
    @ApiOperation("categoryDict字典导入模块")
    @PostMapping("/import")
    public Result importDict(@RequestParam("file") MultipartFile file){
        classmatesService.importExcel(file);
        return Result.ok();
    }
    @ApiOperation("实时的查看同学的作业的提交的情况")
    // @PostMapping("/submit/status/{pageNum}/{pageSize}") 没有多少人不交作业的,没必要也搞不了分页
    @PostMapping("/submit/status")
    public Result submitStatus(/* @PathVariable("pageNum")Integer pageNum,
                               @PathVariable("pageSize")Integer pageSize, */
                               // @RequestBody List<Homework> homeworkList){
                               @RequestBody List<String> homeworkList){
        // Page<SubmitStatus> page = new Page<>(pageNum,pageSize);
        List<SubmitStatus> list = classmatesService.querySubmitStatus(homeworkList);
        return Result.ok(list);
    }
}
