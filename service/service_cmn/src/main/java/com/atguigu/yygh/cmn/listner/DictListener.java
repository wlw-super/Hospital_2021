package com.atguigu.yygh.cmn.listner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DicMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.model.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DicMapper dicMapper;

    //构造注入dicMapper
    public DictListener(DicMapper dicMapper) {
        this.dicMapper = dicMapper;
    }

    //一行一行读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        System.out.println(dictEeVo);
        BeanUtils.copyProperties(dictEeVo,dict);
        dict.setIsDeleted(0);
        //写入数据库
        dicMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
