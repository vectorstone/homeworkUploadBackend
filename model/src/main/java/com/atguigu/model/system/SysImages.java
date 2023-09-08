package com.atguigu.model.system;

import com.atguigu.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/7/2023 1:37 PM
 */
@Data
@ApiModel(description = "用户")
@TableName("user_images")
public class SysImages extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户的姓名")
    @TableField("username")
    private String username;


    @ApiModelProperty(value = "图片的名称")
    @TableField("image_name")
    private String imageName;


    @ApiModelProperty(value = "地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

}
