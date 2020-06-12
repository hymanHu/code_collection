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

import com.zaxxer.hikari.HikariDataSource;

/**
 * @Description: World Db DataSource Config
 * @author: HymanHu
 * @date: 2019-08-16 14:21:51
 */
//@Configuration
// 绑定mapper（dao、api）接口包，service 引入不同包下的mapper则实现了分库，指定对应的sqlSessionFactory
//@MapperScan(basePackages = "com.thornBird.modules.test.dao", sqlSessionFactoryRef = "worldDbSqlSessionFactory")
public class WorldDbDataSourceConfig {

//	/**
//	 * Read 配置文件，装载 worldDbDataSource，放入Spring 容器
//	 * Spring boot使用连接池，返回 HikariDataSource
//	 */
//	@Bean(name="worldDbDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.world")
//	public HikariDataSource getWorldDbDataSource() {
////		return DataSourceBuilder.create().build();
//		return new HikariDataSource();
//	}
//	
//	/**
//	 * Use 已经创建好的 worldDbDataSource 创建 worldDbSqlSessionFactory，放入 Spring 容器
//	 * @Qualifier ---- 查找容器中的bean
//	 */
//	@Bean(name="worldDbSqlSessionFactory")
//	public SqlSessionFactory worldDbSqlSessionFactory(@Qualifier("worldDbDataSource") DataSource datasource)
//            throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(datasource);
//        // 如果使用xml方式，指定对应的xml文件
////        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*Mapper.xml"));
//        return bean.getObject();
//    }
//	
//	/**
//	 * Use 已经创建好的 worldDbSqlSessionFactory 生成 worldDbSqlSessionTemplate，放入 Spring 容器
//	 */
//	@Bean(name="worldDbSqlSessionTemplate")
//	public SqlSessionTemplate worldDbSqlSessionTemplate(
//            @Qualifier("worldDbSqlSessionFactory") SqlSessionFactory sessionfactory) {
//        return new SqlSessionTemplate(sessionfactory);
//    }
}
