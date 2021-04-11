package com.atguigu.easyExcel;


import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;


public class TestWrite {

    public static void main(String[] args) {

        UserData userData = new UserData();
        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userData.setId(i);
            userData.setUsername("zs" + i);
            userDataList.add(userData);
        }

        //设置excel文件路径和文件名称
        String fileName = "F:\\javaCourse\\easyExcel\\01.xlsx";

        //调用方法实现写操作
        EasyExcel.write(fileName, UserData.class).sheet("用户信息").doWrite(userDataList);

    }




}

