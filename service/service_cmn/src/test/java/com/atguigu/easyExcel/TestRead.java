package com.atguigu.easyExcel;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;


public class TestRead {

    public static void main(String[] args) {
        //设置excel文件路径和文件名称
        String fileName = "F:\\javaCourse\\easyExcel\\01.xlsx";

        //调用方法实现写操作
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();

    }




}

