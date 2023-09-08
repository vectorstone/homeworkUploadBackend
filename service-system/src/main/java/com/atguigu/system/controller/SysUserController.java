package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysImages;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.mapper.UserImagesMapper;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/10/2023 7:56 PM
 */
@RestController
@RequestMapping ("/admin/system/sysUser")
@Api(tags = "用户管理模块")
@Transactional
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    UserImagesMapper userImagesMapper;
    @GetMapping("/images/{userId}")
    @ApiOperation("根据userId查询用户的作业的图片的url的集合")
    public Result<List<SysImages>> queryImagesByUserId(@PathVariable("userId")Long userId){
        List<SysImages> sysImages = userImagesMapper.selectList(Wrappers.lambdaUpdate(SysImages.class).eq(SysImages::getUserId, userId));
        return Result.ok(sysImages);
    }

    @PreAuthorize("hasAnyAuthority('bnt.sysUser.list')")
    @ApiOperation("获取用户分页列表")
    @GetMapping("/{page}/{limit}")
    public Result<Page<SysUser>> getUserLists(
            @ApiParam(name = "page",value = "当前页",required = true)
            @PathVariable("page") Integer page,
            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable("limit") Integer limit,
            @ApiParam(name = "sysUserQueryVo",value = "查询条件",required = false)
            SysUserQueryVo sysUserQueryVo
    ){
        Page<SysUser> sysUserIPage = new Page<>(page,limit);
        return Result.ok(sysUserService.selectPage(sysUserIPage,sysUserQueryVo));
    }

    //根据id查询用户
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.update')")
    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public Result<SysUser> getSysUserById(
         @ApiParam(name = "id",value = "用户id",required = true)
         @PathVariable("id") Integer id
    ){
        return Result.ok(sysUserService.getById(id));
    }

    //新增
    // @PreAuthorize("hasAnyAuthority('bnt.sysUser.add')")
    @PostMapping("")
    @ApiOperation("新增用户")
    public Result addUser(@ApiParam(name = "sysUser",value = "新增的用户数据",required = true)
                              SysUser sysUser){
        //防止传过来的对象里面带的有更新的时间
        // sysUser.setUpdateTime(null);

        //发现用户的创建的时间和更新的时间时区不对晚了8个小时
        //手动的将创建的时间和更新的时间更新进去
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateTime(sysUser.getCreateTime());

        //把用户输入的密码进行MD5加密
        sysUser.setPassword(MD5.encrypt(sysUser.getPassword()));
        //前端里面复用了description这个字段里面填写的是密码,记得清空
        sysUser.setDescription(null);
        sysUser.setStatus(1);

        //有的人不删除提示的那个内容,所以增加一个判断头像的链接中是否包含中文
        Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(sysUser.getHeadUrl());

        if (StringUtils.isBlank(sysUser.getHeadUrl()) || m.find()){
            //如果用户没有设置头像的链接的话,那么就给设置一个默认值
            sysUser.setHeadUrl("https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/20180629210546_CQARA.jpeg");
        }
        boolean save = sysUserService.save(sysUser);
        //新增的用户默认的角色就是学生
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(31L);
        sysUserRoleMapper.insert(sysUserRole);

        if(save){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }
    //修改1 data传参
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.add')")
    @PutMapping("")
    @ApiOperation("修改用户:data传参")
    public Result editUser(
            @ApiParam(name = "sysUser",value = "修改的用户数据",required = true)
            @RequestBody SysUser sysUser
    ){
        //updateTime有数据库自己生成,防止用户修改这个时间,所以在这里设置为空
        sysUser.setUpdateTime(null);
        boolean b = sysUserService.updateById(sysUser);
        return Result.ok();
    }
    //修改2 params 传参
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.add')")
    @PutMapping("/test")
    @ApiOperation("修改用户:params传参")
    public Result editUser1(
            @ApiParam(name = "sysUser",value = "修改的用户数据",required = true)
            SysUser sysUser
    ){
        //updateTime有数据库自己生成,防止用户修改这个时间,所以在这里设置为空
        sysUser.setUpdateTime(null);
        boolean b = sysUserService.updateById(sysUser);
        return Result.ok();
    }
    //根据id删除
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.remove')")
    @ApiOperation("根据id删除用户")
    @DeleteMapping("/{id}")
    public Result removeById(
            @ApiParam(name = "id",value = "用户id",required = true)
            @PathVariable("id") Integer id
    ){
        sysUserService.removeById(id);
        return Result.ok();
    }

    //根据id批量删除
    @PreAuthorize("hasAnyAuthority('bnt.sysUser.remove')")
    @ApiOperation("批量删除用户")
    @DeleteMapping("/remove")
    public Result removeBatch(
            @ApiParam(name = "idList",value = "用户id集合或数组",required = true)
            @RequestBody List<Integer> idList
    ){
        sysUserService.removeByIds(idList);
        return Result.ok();
    }
}
