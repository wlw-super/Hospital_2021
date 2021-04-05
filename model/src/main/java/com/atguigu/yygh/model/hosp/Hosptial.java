package com.atguigu.yygh.model.hosp;

import com.atguigu.yygh.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Hosptial extends BaseEntity {


    @Indexed //普通索引
    @TableField(value = "hosname")
    private String hosname;

    //@Indexed(unique = true) //唯一索引
    @TableField(value = "hoscode")
    private String hoscode;

    @TableField(value = "api_url")
    private String apiUrl;

    @TableField(value = "sign_key")
    private String signKey;

    @TableField(value = "contacts_name")
    private String contactsName;

    @TableField(value = "contacts_phone")
    private String contactsPhone;

    @TableField(value = "contacts_status")
    private Integer status;

}
