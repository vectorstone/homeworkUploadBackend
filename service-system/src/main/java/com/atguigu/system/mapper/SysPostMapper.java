package com.atguigu.system.mapper;

import com.atguigu.model.system.SysPost;
import com.atguigu.model.vo.SysPostQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPostMapper extends BaseMapper<SysPost> {
    Page<SysPost> getPageList(Page<SysPost> pageParam, @Param("vo") SysPostQueryVo sysPostQueryVo);
}
