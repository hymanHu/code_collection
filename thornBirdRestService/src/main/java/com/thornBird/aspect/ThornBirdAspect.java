package com.thornBird.aspect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description: Thorn Bird Aspect
 * @author: HymanHu
 * @date: 2019-08-05 14:13:41
 */
@Aspect
@Component
public class ThornBirdAspect {
	private final static Logger LOGGER = LoggerFactory.getLogger(ThornBirdAspect.class);
	
	@Resource(name="dynamicDataSource")
//	@Autowired
	private DataSource dataSource;

	/**
	 * 第一个*代表返回类型不限
	 * 第二个*代表module下所有子包
	 * 第三个*代表所有类
	 * 第四个*代表所有方法
	 * (..) 代表参数不限
	 * Order 代表优先级，数字越小优先级越高
	 */
	@Before(value = "com.thornBird.aspect.ThornBirdPonitCut.controllerPointCut()")
	public void beforeController(JoinPoint joinPoint) {
		LOGGER.debug("=======================================================");
		LOGGER.debug("Before Controller.");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		LOGGER.debug("请求来源：" + request.getRemoteAddr());
		LOGGER.debug("请求URL：" + request.getRequestURL().toString());
		LOGGER.debug("请求方式：" + request.getMethod());
		LOGGER.debug("响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + 
				joinPoint.getSignature().getName());
		LOGGER.debug("请求参数：" + Arrays.toString(joinPoint.getArgs()));
	}
	
	@Around(value = "com.thornBird.aspect.ThornBirdPonitCut.controllerPointCut()")
	public Object aroundControlle(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		LOGGER.debug("Around Controlle.");
		return proceedingJoinPoint.proceed();
	}
	
	@After(value = "com.thornBird.aspect.ThornBirdPonitCut.controllerPointCut()")
	public void afterController() {
		LOGGER.debug("After Controller.");
		LOGGER.debug("=======================================================");
	}
	
	@Before("execution(public * com.thornBird.modules..*.dao.*.*.*(..))")
	public void beforeDao() {
		if (dataSource == null) {
			LOGGER.debug("数据源未初始化。");
		} else {
			// 此处再次获取链接，可能导致 HikariPool-1 - Connection is not available, request timed out after 30002ms.
//			try {
//				Connection connection = dataSource.getConnection();
//				LOGGER.debug("当前数据源： " + connection.getCatalog());
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	}
}
