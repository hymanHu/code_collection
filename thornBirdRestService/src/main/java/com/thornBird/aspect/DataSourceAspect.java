package com.thornBird.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.thornBird.config.dataSource.aopPart.DataBaseAnnotation;
import com.thornBird.config.dataSource.aopPart.DataBaseHolder;

/**
 * @Description: Data Source Aspect ---- 切面程序，对方法设置数据源
 * @author: HymanHu
 * @date: 2019-08-16 21:29:51
 */
@Aspect
@Component
@Order(1)
public class DataSourceAspect {
	
	@Pointcut("@annotation(com.thornBird.config.dataSource.aopPart.DataBaseAnnotation)")
	public void annotationPointCut() {}
	
	@Before(value = "annotationPointCut()&&@annotation(dataBaseAnnotation)")
	public void beforeSwitchDS(JoinPoint point, DataBaseAnnotation dataBaseAnnotation) {
		DataBaseHolder.setDataBase(dataBaseAnnotation.value());
	}
	
	@After(value = "annotationPointCut()")
	public void afterSwitchDS(JoinPoint point) {
		DataBaseHolder.clearDataBase();
	}
}
