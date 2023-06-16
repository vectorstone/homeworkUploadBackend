package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysMenuService;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/9/2023 8:22 PM
 */
@Api(tags="登录模块")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(
            @ApiParam(name = "loginVo",value = "用户登录信息(帐号和密码)",required = true)
            @RequestBody LoginVo loginVo
            ){
        //1.首先根据用户名去数据库中查询用户
        SysUser sysUser = sysUserService.getSysUserByUserName(loginVo.getUsername());

        //2.三重校验(用户是否存在,密码是否正确,用户状态是否合法)
        //2.1用户是否存在校验
        if(sysUser == null){
            //214账户错误
            throw new GuiguException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        //2.2校验密码是否正确
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())){
            //214密码错误
            throw new GuiguException(ResultCodeEnum.PASSWORD_ERROR);
        }
        //2.3用户状态是否合法校验
        if(sysUser.getStatus() != 1){
            //217账户已被停用
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //3.生成UUID作为token,将token存入redis数据库
        //可以使用jwt来代替uuid生成用户的token
        String token = UUID.randomUUID().toString().replaceAll("-","");

        //将token设置到redis的缓存里面,设置过期时间为2hours
        redisTemplate.boundValueOps(token).set(sysUser,2, TimeUnit.HOURS);
        //将token返回给前端
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    @ApiOperation("after login , get some the user information")
    @GetMapping("/info")
    public Result getInfo(
            @ApiParam(name = "request",value = "前端发过来的查询用户信息的请求",required = true)
            HttpServletRequest request
    ){
        /*Map<String,Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","Gavin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");*/

        //从请求头中获取token
        String token = request.getHeader("token");
        //根据token从redis数据库中获取用户的id信息
        SysUser sysUser = (SysUser) redisTemplate.boundValueOps(token).get();

        //获取用户的信息(用户信息,菜单信息,对应的按钮权限)
        Map<String,Object> userInfoMap = sysUserService.getUserInfoByUserId(sysUser.getId());

        return Result.ok(userInfoMap);
    }

    @ApiOperation("logout")
    @PostMapping("/logout")
    public Result logout(
            @ApiParam(name = "request",value = "HttpServletRequest请求",required = true)
            HttpServletRequest request
    ){
        //获取请求头中的token
        String token = request.getHeader("token");

        //从redis数据库中删除该用户的token
        redisTemplate.delete(token);

        return Result.ok();
    }
}
