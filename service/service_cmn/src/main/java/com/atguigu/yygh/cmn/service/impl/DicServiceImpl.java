package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listner.DictListener;
import com.atguigu.yygh.cmn.mapper.DicMapper;
import com.atguigu.yygh.cmn.service.DicService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.model.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dict> implements DicService {

//    @Autowired
//    private DicMapper dicMapper;就是baseMapper

    //根据数据id查询数据列表
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            boolean hasChild = hasChild(dict.getId());
            dict.setHasChildren(hasChild);
        }
        return dicts;
    }


    //导出excel
    @Override
    public void exportDictData(HttpServletResponse response) {
        //设置下载信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        //这里设置的 URLEncoder.encode防止文件名中文乱码，与easyexcel无关
        //String fileName = URLEncoder.encode("数据字典", "UTF-8");
        String fileName = "dict";
        response.setHeader("Content-disposition","attachment:filename="+fileName+".xlsx");

        //查询数据库
        List<Dict> dicts = baseMapper.selectList(null);
        //封装到DictEeVo
        List<DictEeVo> dictEeVoList = new ArrayList<>();
        for (Dict dict: dicts) {
            DictEeVo dictEeVo = new DictEeVo();
            //使用工具类复制对象
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }

        //写到excel中
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictEeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //导入excel
    @Override
    @CacheEvict(value = "dict", allEntries = true)//清空在dict下的所有缓存
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断id下面是否有子节点
    public boolean hasChild(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer selectCount = baseMapper.selectCount(queryWrapper);
        return selectCount>0;
    }




}
