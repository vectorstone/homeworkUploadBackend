package com.atguigu.model.vo;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 7/28/2023 9:03 PM
 */
@Data
@ApiModel("excel导入数据的时候对应的类")
public class ClassmatesExcel {
    @ApiModelProperty(value = "id")
    @ExcelProperty(index = 0,value = "序号")
    private Long id;

    @ApiModelProperty(name = "name",value = "姓名")
    @ExcelProperty(index = 1,value = "姓名")
    private String name;

    @ApiModelProperty(name = "grade",value = "班级")
    @ExcelProperty(index = 2,value = "班级")
    private String grade;

    @ApiModelProperty(name = "group",value = "小组")
    @ExcelProperty(index = 3,value = "小组")
    private Integer group;

    @ApiModelProperty(name = "nickName",value = "昵称")
    @ExcelProperty(index = 4,value = "昵称")
    private String nickName;

    @ApiModelProperty(name = "phone",value = "手机号码")
    @ExcelProperty(index = 5,value = "手机号码")
    private String phone;



}
