package com.atguigu.model.system;

import com.atguigu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/9/2023 11:17 AM
 */
@Data
@ApiModel(description = "作业表")
@TableName("homework")
public class Homework extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作业的名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "作业的描述")
    @TableField("description")
    private String description;
}
