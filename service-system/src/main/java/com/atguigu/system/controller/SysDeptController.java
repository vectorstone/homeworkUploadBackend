package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysDept;
import com.atguigu.model.system.SysMenu;
import com.atguigu.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/15/2023 4:05 PM
 */
@Api(tags = "部门管理模块")
@RestController
@RequestMapping("/admin/system/sysDept")
@Transactional
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("获取所有的部门信息")
    @GetMapping("")
    public Result<List<SysDept>> getDeptList(){
        return Result.ok(sysDeptService.finNodes());
    }

    //增加部门
    @PreAuthorize("hasAnyAuthority('bnt.sysDept.add')")
    @PostMapping("")
    @ApiOperation("增加部门")
    public Result save(
            @ApiParam(name = "sysDept",value = "需要增加的部门对象",required = true)
            @RequestBody SysDept sysDept
    ){
        sysDeptService.save(sysDept);
        return Result.ok();
    }

    //修改菜单
    @PreAuthorize("hasAnyAuthority('bnt.sysDept.update')")
    @PutMapping("")
    @ApiOperation("修改部门")
    public Result update(
            @ApiParam(name = "sysDept",value = "需要修改的部门对象",required = true)
            @RequestBody SysDept sysDept
    ){
        sysDeptService.updateById(sysDept);
        return Result.ok();
    }

    //根据id删除菜单
    @PreAuthorize("hasAnyAuthority('bnt.sysDept.remove')")
    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除部门")
    public Result deleteMenu(
            @ApiParam(name = "id",value = "需要删除的菜单的id",required = true)
            @PathVariable("id") Long id
    ){
        sysDeptService.removeDeptById(id);
        return Result.ok();
    }
}
