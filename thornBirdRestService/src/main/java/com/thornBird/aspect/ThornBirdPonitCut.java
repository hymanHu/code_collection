package com.thornBird.aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * @Description: Thorn Bird Ponit Cut
 * @author: HymanHu
 * @date: 2019-08-05 14:03:44
 */
public class ThornBirdPonitCut {

	@Pointcut("execution(public * com.thornBird.modules.test.controller.JpaController.getCities(..))")
	@Order(2)
	public void jpaControllerPointCut() {}
	
	/**
	 * 第一个*代表返回类型不限
	 * 第二个*代表module下所有子包
	 * 第三个*代表所有类
	 * 第四个*代表所有方法
	 * (..) 代表参数不限
	 * Order 代表优先级，数字越小优先级越高
	 */
	@Pointcut("execution(public * com.thornBird.modules..*.controller.*.*(..))")
	@Order(2)
	public void controllerPointCut() {}
	
	@Pointcut("execution(public * com.thornBird.modules..*.services.*.*(..))")
	@Order(2)
	public void servicePointCut() {}
	
	@Pointcut("execution(public * com.thornBird.modules..*.api.*.*(..))")
	@Order(2)
	public void apiPointCut() {}
	
	
}
