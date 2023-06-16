package com.atguigu.system.service.impl;

import com.atguigu.common.helper.DeptHelper;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysDept;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysDeptMapper;
import com.atguigu.system.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/15/2023 4:07 PM
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Override
    public List<SysDept> finNodes() {
        //先查询获得所有的dept信息
        /*QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);*/
        List<SysDept> sysDepts = sysDeptMapper.selectList(null);
        //使用工具类将查询获得的dept的list构建成树状
        return DeptHelper.buildTree(sysDepts,0L);
    }

    @Override
    public void removeDeptById(Long id) {
        //先查询这个id下面有没有子节点
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = sysDeptMapper.selectCount(queryWrapper);
        if(count > 0){
            //说明还有子节点,所以不能删除
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }else{
            sysDeptMapper.deleteById(id);
        }
    }
}
