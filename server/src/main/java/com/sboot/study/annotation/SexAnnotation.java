package com.sboot.study.annotation;

import com.sboot.study.annotation.validate.SexValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * create by faraway on 2018/12/21
 * description:@SexAnnotion注解：性別校验
 * 注解：用于描述数据的元数据
 */
//@Documented表明这个注释是由javadoc记录的，如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。
@Documented
//@Constraint指定校验逻辑
@Constraint(validatedBy = {SexValidation.class})
//@Target说明了Annotation所修饰的对象范围 可重复声明
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
//表示有效期为运行期
@Retention(RetentionPolicy.RUNTIME)
public @interface SexAnnotation {

    String message() default "性别校验取值为：1=男;2=女";

    /**下面两个不加报错：SexAnnotation包含约束注释，但不包含组参数*/
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
/** 类, 接口 (包括注释类型), 或 枚举 声明 */
//TYPE

/** 字段声明（包括枚举常量） */
//FIELD

/** 方法声明(Method declaration) */
//METHOD

/** 参数声明 */
//PARAMETER

/** 构造函数声明 */
//CONSTRUCTOR

/** 局部变量声明 */
//LOCAL_VARIABLE

/** 注释类型声明 */
//ANNOTATION_TYPE

/** 包声明 */
//PACKAGE

/** 类型参数声明 */
//TYPE_PARAMETER

/** 使用的类型 */
//TYPE_USE
