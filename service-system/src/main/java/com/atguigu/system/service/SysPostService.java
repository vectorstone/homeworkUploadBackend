package com.atguigu.system.service;

import com.atguigu.model.system.SysPost;
import com.atguigu.model.vo.SysPostQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysPostService extends IService<SysPost> {
    //获取岗位列表,带分页的
    Page<SysPost> getSysPostList(Page<SysPost> pageParam, SysPostQueryVo sysPostQueryVo);
}
