package com.thornBird.config.dataSource.aopPart;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: Data Base Annotation ---- 定义数据源注解
 * @author: HymanHu
 * @date: 2019-08-17 21:36:30
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBaseAnnotation {

	String value() default "maindb";
}
