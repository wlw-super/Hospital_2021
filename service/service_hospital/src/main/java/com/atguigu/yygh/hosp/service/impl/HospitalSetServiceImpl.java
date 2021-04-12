package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.hosp.mapper.HospitalSetMapper;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;


@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService{

    @Override
    public String getSignKeyByHoscode(String hoscode) {
        QueryWrapper<HospitalSet> query = new QueryWrapper<>();
        query.eq("hoscode", hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(query);
        return hospitalSet.getSignKey();
    }
}