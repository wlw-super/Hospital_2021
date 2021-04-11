package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DicService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DicService dicService;

    //根据数据id查询子数据列表
    @ApiOperation(value = "查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable("id") Long id) {
        List<Dict> dictList = dicService.findChildData(id);
        return Result.ok(dictList);
    }

    //导出数据字典的接口
    @ApiOperation(value = "导出数据字典")
    @GetMapping("exportData")
    public Void exportDict(HttpServletResponse response) {
        dicService.exportDictData(response);
        return null;
    }

    //导入数据字典
    @ApiOperation(value = "导出数据字典")
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        dicService.importDictData(file);
        return Result.ok();
    }
}
