package com.sboot.study.request;

import com.sboot.study.annotation.SexAnnotation;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * create by faraway on 2018/12/21
 * description:
 */

@Data
@ToString
public class ValidateUser {

    @NotBlank
    private String userName;

    @SexAnnotation
    private Integer sex;

    @NotNull
    @Min(18)
    private Integer age;
}
