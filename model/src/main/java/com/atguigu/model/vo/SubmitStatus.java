package com.atguigu.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 9/10/2023 11:08 PM
 */
@ApiModel(description = "用于给前端返回作业提交情况的vo对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitStatus {
    @ApiModelProperty(value = "同学的姓名")
    private String name;
    @ApiModelProperty(value = "未提交的作业的名称")
    private String unSubmit;
}
