package com.atguigu.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysPostQueryVo implements Serializable {
	
	//@ApiModelProperty(value = "岗位编码")
	private String postCode;

	//@ApiModelProperty(value = "岗位名称")
	private String name;

	//@ApiModelProperty(value = "状态（1正常 0停用）")
	private Boolean status;

}

