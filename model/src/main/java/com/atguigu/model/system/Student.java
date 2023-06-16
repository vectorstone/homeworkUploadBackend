package com.atguigu.model.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/12/2023 3:12 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer score;
    private String gender;
}
