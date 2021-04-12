package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    //上传医院的接口方法
    void save(Map<String, Object> paraMap);

    Hospital getByHoscode(String hoscode);
}