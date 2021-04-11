package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.model.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin//跨域访问
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    @ApiOperation(value = "查询所有医院列表")
    @GetMapping("findAll")
    public Result test() {
        List<HospitalSet> lists = hospitalSetService.list();
        return Result.ok(lists);
    }

    @ApiOperation(value = "逻辑删除医院")
    @DeleteMapping("deleteById/{id}")
    public Result removeHospSet(@PathVariable Long id) {
        Boolean result = hospitalSetService.removeById(id);
        if (result)
            return Result.ok();
        else
            return Result.fail();
    }


    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPage/{currentPage}/{limit}")
    public Result findPageHospSet(@PathVariable long currentPage, @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {

        //当前页数，每页数据量，医院代码，名称
        Page<HospitalSet> page = new Page<>(currentPage, limit);
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();

        //构造查询条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(hoscode))
            wrapper.eq("hoscode", hoscode);
        if (!StringUtils.isEmpty(hosname))
            wrapper.like("hosname", hosname);

        //调用方法实现分页查询
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);

        return Result.ok(hospitalSetPage);
    }

    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result findPageHospSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save)
            return Result.ok();
        else
            return Result.fail();
    }

    @ApiOperation(value = "根据id查询医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        /*try {
            int a = 1 / 0;
        } catch (Exception ex){
            throw new YyghException("除0异常", 100);
        }*/
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation(value = "根据id更改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = false;
        HospitalSet existFlag = hospitalSetService.getById(hospitalSet.getId());
        if (null != existFlag)
            flag = hospitalSetService.updateById(hospitalSet);

        if (flag)
            return Result.ok();
        else
            return Result.fail();
    }

    @ApiOperation(value = "批量删除医院设置")
    @PostMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids) {
        boolean flag = hospitalSetService.removeByIds(ids);
        if (flag)
            return Result.ok();
        else
            return Result.fail();
    }

    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag)
            return Result.ok();
        else
            return Result.fail();
    }


    @ApiOperation(value = "发送签名key",notes = "医院信息配置后,可以通过短信的形式发送医院编号与签名key给联系人,联系人拿到该信息就可以参考《尚医通API接口文档.docx》对接接口了。")
    @PutMapping("sendId/{id}")
    public Result sendId(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String hoscode = hospitalSet.getHoscode();
        String signKey = hospitalSet.getSignKey();

        //todo 发送短信
        return Result.ok();
    }
}
