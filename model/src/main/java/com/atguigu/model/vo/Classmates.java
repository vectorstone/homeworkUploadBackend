package com.atguigu.model.vo;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 7/28/2023 9:03 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("数据库中对应的同学的类")
@TableName("classmates")
public class Classmates implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(name = "name",value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(name = "grade",value = "班级")
    @TableField(value = "grade")
    private String grade;

    @ApiModelProperty(name = "group",value = "小组")
    @TableField(value = "`group`")
    private Integer group;

    @ApiModelProperty(name = "nickName",value = "昵称")
    @TableField(value = "nickName")
    private String nickName;

    @ApiModelProperty(name = "phone",value = "手机号码")
    @TableField(value = "phone")
    private String phone;



    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time")
    private Date createTime;


    @ApiModelProperty(value = "创建日期")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记(0:不可用 1:可用)")
    @TableField(value = "is_deleted")
    @TableLogic
    private Boolean deleted;
}
