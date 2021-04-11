package com.atguigu.yygh.model.hosp;


import com.atguigu.yygh.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "医院设置")
@Data
@TableName(value = "hospital_set")
public class HospitalSet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院名称")
    @TableField(value = "hosname")
    private String hosname;

    @ApiModelProperty(value = "医院代码")
    @TableField(value = "hoscode")
    private String hoscode;

    @ApiModelProperty(value = "api基础路径")
    @TableField(value = "api_url")
    private String apiUrl;

    @ApiModelProperty(value = "签名秘钥")
    @TableField(value = "sign_key")
    private String signKey;

    @ApiModelProperty(value = "联系人姓名")
    @TableField(value = "contacts_name")
    private String contactsName;

    @ApiModelProperty(value = "联系人电话")
    @TableField(value = "contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    private Integer status;

}
