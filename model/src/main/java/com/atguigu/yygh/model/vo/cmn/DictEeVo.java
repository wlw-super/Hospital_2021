package com.atguigu.yygh.model.vo.cmn;


import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "excel读写类")
@Data
public class DictEeVo {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id",index = 0)
    private Long id;

    @ApiModelProperty(value = "上级id")
    @ExcelProperty(value = "上级id",index = 1)
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称",index = 2)
    private String name;

    @ApiModelProperty(value = "值")
    @ExcelProperty(value = "值",index = 3)
    private String value;

    @ApiModelProperty(value = "编码")
    @ExcelProperty(value = "编码",index = 4)
    private String dictCode;

}
