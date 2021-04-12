package com.atguigu.yygh.hosp.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void save(Map<String, Object> paraMap) {

        //把参数map集合转换为对象Hospital
        String jsonString = JSONObject.toJSONString(paraMap);
        Hospital hospital = JSONObject.parseObject(jsonString, Hospital.class);

        //判断是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital existHospital = hospitalRepository.getHospitalByHoscode(hoscode);

        //存在
        if (null != existHospital) {
            hospital.setId(existHospital.getId());
            hospital.setStatus(existHospital.getStatus());
            hospital.setCreateTime(existHospital.getCreateTime());
            hospital.setIsDeleted(0);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        } else {
            //不存在
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setIsDeleted(0);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }
}
