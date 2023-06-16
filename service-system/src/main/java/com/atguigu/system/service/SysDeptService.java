package com.atguigu.system.service;

import com.atguigu.model.system.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {
    //获取所有的菜单信息
    List<SysDept> finNodes();

    //根据id删除部门
    void removeDeptById(Long id);
}
