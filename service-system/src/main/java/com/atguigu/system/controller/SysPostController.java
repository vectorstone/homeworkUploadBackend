package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysPost;
import com.atguigu.model.vo.SysPostQueryVo;
import com.atguigu.system.service.SysPostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/15/2023 1:54 PM
 */
@Api(tags = "岗位管理模块")
@RequestMapping("/admin/system/sysPost")
@RestController
@Transactional
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;

    //获取岗位列表,带分页的
    @GetMapping("/{page}/{limit}")
    @ApiOperation("获取岗位列表,带分页信息的")
    public Result<Page<SysPost>> getSysPostList(
            @ApiParam(name = "page",value = "当前页码",required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit",value = "页面内容的大小",required = true)
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "sysPostQueryVo",value = "封装了查询条件的对象")
            SysPostQueryVo sysPostQueryVo
    ){
        Page<SysPost> pageParam = new Page<>(page,limit);
        Page<SysPost> pageList = sysPostService.getSysPostList(pageParam,sysPostQueryVo);
        return Result.ok(pageList);
    }

    //增加
    @ApiOperation("增加岗位")
    @PostMapping("")
    public Result add(
            @ApiParam(name = "sysPost",value = "要新增的岗位对象",required = true)
            @RequestBody SysPost sysPost
    ){
        sysPostService.save(sysPost);
        return Result.ok();
    }

    //修改
    @ApiOperation("修改岗位")
    @PutMapping("")
    public Result edit(
            @ApiParam(name = "sysPost",value = "要新增的岗位对象",required = true)
            @RequestBody SysPost sysPost
    ){
        sysPostService.updateById(sysPost);
        return Result.ok();
    }

    //删除
    @ApiOperation("删除岗位")
    @DeleteMapping("/{id}")
    public Result remove(
            @ApiParam(name = "id",value = "要的岗位的id",required = true)
            @PathVariable Long id
    ){
        sysPostService.removeById(id);
        return Result.ok();
    }
}
