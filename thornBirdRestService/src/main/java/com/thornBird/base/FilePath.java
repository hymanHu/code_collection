package com.thornBird.base;

/**
 * @Description: File Path
 * @author: HymanHu
 * @date: 2019-08-16 13:30:10
 */
public class FilePath {
	public static void main(String[] args) {
		System.out.println("==============");
		System.out.println("当前项目根目录:" + System.getProperty("user.dir"));
		System.out.println("当前classPath路径:" + Thread.currentThread().getContextClassLoader().getResource("").getPath());
		System.out.println("当前类classPath路径(不包含自己):" + FilePath.class.getResource("").getPath());
		System.out.println("配置文件路径1：" + FilePath.class.getResource("/logback-dev.xml").getPath());
		System.out.println("配置文件路径2：" + FilePath.class.getClassLoader().getResource("logback-dev.xml").getPath());
		System.out.println("==============");
	}
}
