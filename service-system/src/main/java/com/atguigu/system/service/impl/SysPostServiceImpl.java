package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysPost;
import com.atguigu.model.vo.SysPostQueryVo;
import com.atguigu.system.mapper.SysPostMapper;
import com.atguigu.system.service.SysPostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/15/2023 1:57 PM
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
    @Autowired
    private SysPostMapper sysPostMapper;
    @Override
    public Page<SysPost> getSysPostList(Page<SysPost> pageParam, SysPostQueryVo sysPostQueryVo) {
        Page<SysPost> pageList = sysPostMapper.getPageList(pageParam,sysPostQueryVo);
        return pageList;
    }
}
