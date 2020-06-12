package com.thornBird.config.dataSource.packagePart;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @Description: MainDb DataSource Config
 * @author: HymanHu
 * @date: 2019-08-16 14:21:51
 */
//@Configuration
// 绑定mapper（dao、api）接口包，service 引入不同包下的mapper则实现了分库，指定对应的sqlSessionFactory
//@MapperScan(basePackages = "com.thornBird.modules.test.dao.mainDb", sqlSessionFactoryRef = "mainDbSqlSessionFactory")
public class MainDbDataSourceConfig {

//	/**
//	 * Read 配置文件，装载 mainDbDataSource，放入Spring 容器，并设定为默认数据源
//	 * Spring boot使用连接池，返回 HikariDataSource
//	 */
//	@Primary
//	@Bean(name="mainDbDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.maindb")
//	public HikariDataSource getMainDbDataSource() {
////		return DataSourceBuilder.create().build();
//		return new HikariDataSource();
//	}
//	
//	/**
//	 * Use 已经创建好的 mainDbDataSource 创建 mainDbSqlSessionFactory，放入 Spring 容器，并设定为默认
//	 * @Qualifier ---- 查找容器中的bean
//	 */
//	@Primary
//	@Bean(name="mainDbSqlSessionFactory")
//	public SqlSessionFactory mainDbSqlSessionFactory(@Qualifier("mainDbDataSource") DataSource datasource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(datasource);
//        // 如果使用xml方式，指定对应的xml文件
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*Mapper.xml"));
//        return bean.getObject();
//    }
//	
//	/**
//	 * Use 已经创建好的 mainDbSqlSessionFactory 生成 mainDbSqlSessionTemplate，放入 Spring 容器，并设置为默认
//	 */
//	@Primary
//	@Bean(name="mainDbSqlSessionTemplate")
//	public SqlSessionTemplate mainDbSqlSessionTemplate(
//            @Qualifier("mainDbSqlSessionFactory") SqlSessionFactory sessionfactory) {
//        return new SqlSessionTemplate(sessionfactory);
//    }
}
