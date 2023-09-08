package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.util.R;
import com.atguigu.model.system.SysUser;
import com.atguigu.system.service.OssService;
import com.atguigu.system.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 7/6/2023 7:41 PM
 */
@RestController
@RequestMapping("/api/oss")
@Api(tags = "oss文件管理模块")
public class OssController {
    @Autowired
    OssService ossService;

    // requestParam方式传文件的方法
    @PostMapping("/upload")
    @ApiOperation("文件上传requestParam的方式")
    public R upload(MultipartFile file,
                    @RequestParam("module") String module, HttpServletRequest request, HttpServletResponse response) {
        // file指的是需要上传的文件的对象
        // module指的是模块的名称,也就是bucket里面保存文件的目录名

        String path = ossService.upload(file, module,request,response);
        // 将上传成功后的文件的路径返回给前端,用来做回显
        // return Result.ok(path); // TODO 这个地方有修改前端里面需要注意下
        return R.ok(); // TODO 这个地方有修改前端里面需要注意下
    }

    // 删除文件的方法
    @ApiOperation("删除文件")
    @DeleteMapping
    public R deleteFile(@RequestParam("path") String path) {
        ossService.deleteByPath(path);
        return R.ok();
    }
}
