package com.atguigu.easyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData {

    @ExcelProperty(value = "用户编号", index = 0)
    private  int id;

    @ExcelProperty(value = "名字", index = 1)
    private String username;


}
