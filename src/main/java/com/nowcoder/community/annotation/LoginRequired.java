package com.nowcoder.community.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 注解写在方法上
@Retention(RetentionPolicy.RUNTIME)   // 注解的有效时长：程序运行时
public @interface LoginRequired {
}
