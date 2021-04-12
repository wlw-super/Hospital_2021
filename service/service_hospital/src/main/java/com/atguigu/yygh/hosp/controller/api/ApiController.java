package com.atguigu.yygh.hosp.controller.api;


import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.model.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.model.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;


    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        //获取hospital_manager传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        //转换为Map<String, Object>类型
        Map<String, Object> paraMap = HttpRequestHelper.switchMap(requestParameterMap);

        //1.获取hospital_manager传递过来的签名
        String hospSign = (String) paraMap.get("sign");
        //2.通过hoscode查询数据库yygh_hosp中hospital_set表中对应的签名
        String hoscode = (String) paraMap.get("hoscode");
        String signKey = hospitalSetService.getSignKeyByHoscode(hoscode);
        //3.把查到的signKey进行Md5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //5.传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String) paraMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paraMap.put("logoData", logoData);

        //.调用service的方法
        hospitalService.save(paraMap);
        return Result.ok();
    }

    //查询医院
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        //获取医院传递的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //验签
        verifySign(paramMap);
        //查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);

        return Result.ok(hospital);
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //获取医院传递的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);
        //验签
        verifySign(paramMap);
        //调用service
        departmentService.save(paramMap);

        return Result.ok();
    }

    //查询科室接口
    @PostMapping("department/list")
    public Result listDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = paramMapSwitch(request);
        verifySign(paramMap);

        //医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //当前页 每页数据量
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        //调用service
        Page<Department> departmentPage = departmentService.findPageDepartment(page, limit, departmentQueryVo);

        return Result.ok(departmentPage);
    }


    //删除医院接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = paramMapSwitch(request);
        verifySign(paramMap);
        //医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //科室编号
        String depcode = (String) paramMap.get("depcode");
        //调用service
        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    //添加排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = paramMapSwitch(request);
        verifySign(paramMap);
        //调用service
        scheduleService.save(paramMap);
        return Result.ok();
    }

    //查询排班接口
    @PostMapping("schedule/list")
    public Result listSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = paramMapSwitch(request);
        verifySign(paramMap);

        //医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //科室编号
        String depcode = (String) paramMap.get("depcode");

        //当前页 每页数据量
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        //调用service
        Page<Schedule> schedulePage = scheduleService.findPageSchedule(page, limit, scheduleQueryVo);

        return Result.ok(schedulePage);
    }

    //删除排班接口
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = paramMapSwitch(request);
        verifySign(paramMap);
        //医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //科室编号
        String hosScheduleId = (String) paramMap.get("hosScheduleId");
        //调用service
        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }




    /**
     * 获取医院传递的信息
     * @param request
     * @return
     */
    private Map<String, Object> paramMapSwitch(HttpServletRequest request) {
        //获取医院传递的信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        return paramMap;
    }

    /**
     * 验签
     * @param paramMap
     */
    private void verifySign(Map<String, Object> paramMap) {
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //验签
        String hospSign = (String) paramMap.get("sign");
        String signKey = hospitalSetService.getSignKeyByHoscode(hoscode);
        String encrypt = MD5.encrypt(signKey);
        if (!hospSign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }


}
