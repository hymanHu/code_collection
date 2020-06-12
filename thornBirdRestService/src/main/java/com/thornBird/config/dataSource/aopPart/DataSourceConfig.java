package com.thornBird.config.dataSource.aopPart;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @Description: Data Source Config ---- 配置多数据源
 * @author: HymanHu
 * @date: 2019-08-16 20:52:53
 */
@Configuration
@EnableTransactionManagement
// for mybatis
@MapperScan(
		sqlSessionFactoryRef = "sqlSessionFactory",
		sqlSessionTemplateRef="sqlSessionTemplate",
		basePackages = "com.thornBird.modules.*.dao")
// for jpa
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactory",
        transactionManagerRef="jpaTransactionManager",
        basePackages= { "com.thornBird.modules.*.dao"})
public class DataSourceConfig {
	
	// hikari config
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int maximumPoolSize;
	@Value("${spring.datasource.hikari.minimum-idle}")
	private int minimumIdle;
	@Value("${spring.datasource.hikari.idle-timeout}")
	private long idleTimeout;
	@Value("${spring.datasource.hikari.auto-commit}")
	private boolean autoCommit;
	
	// mybatis config
	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;
	@Value("${mybatis.config-locations}")
	private String configLocations;
	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;
	@Value("${mybatis.configuration.mapUnderscoreToCamelCase}")
	private boolean mapUnderscoreToCamelCase;
	
	/**
	 * init Hikari Data Source
	 */
	public HikariDataSource initHikariDataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setMaximumPoolSize(maximumPoolSize);
		hikariDataSource.setMinimumIdle(minimumIdle);
		hikariDataSource.setIdleTimeout(idleTimeout);
		hikariDataSource.setAutoCommit(autoCommit);
		return hikariDataSource;
	}

	/**
	 * DataSources config
	 */
	@Primary
	@Bean(name="mainDbDataSourceAop")
	@ConfigurationProperties(prefix = "spring.datasource.maindb")
	public HikariDataSource getMainDataSource() {
		return initHikariDataSource();
//		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="worldDbDataSourceAop")
	@ConfigurationProperties(prefix = "spring.datasource.world")
	public HikariDataSource getWorldDataSource() {
		return initHikariDataSource();
//		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="dynamicDataSource")
	public DataSource getDynamicDataSource(
			@Qualifier("mainDbDataSourceAop") DataSource mainDbDataSourceAop, 
			@Qualifier("worldDbDataSourceAop") DataSource worldDbDataSourceAop) {
		Map<Object, Object> dataSourcesMap = new HashMap<Object, Object>();
		dataSourcesMap.put(DataBaseHolder.DataBase.maindb.toString(), mainDbDataSourceAop);
		dataSourcesMap.put(DataBaseHolder.DataBase.world.toString(), worldDbDataSourceAop);
		
		DataSourceRouting dataSourceRouting = new DataSourceRouting();
		dataSourceRouting.setTargetDataSources(dataSourcesMap);
		dataSourceRouting.setDefaultTargetDataSource(mainDbDataSourceAop);
		
		return dataSourceRouting;
	}
	
	/**
	 * For mybatis config
	 */
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(
			@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		
		// 自定义了SqlSessionFactory，导致mybatis配置无效，在此我们需要手动注入配置内容
		// 不能注入configLocations，因为configuration、configLocations不能共存，系统不知道任哪个配置
        bean.setDataSource(dynamicDataSource);
        bean.setTypeAliasesPackage(typeAliasesPackage);
//        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocations));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        bean.setConfiguration(configuration);
        
        return bean.getObject();
	}
	
	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate mainDbSqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
	
	@Bean(name = "mybatisTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
		return new DataSourceTransactionManager(dynamicDataSource);
	}
	
	/**
	 * for jpa config
	 */
	@Autowired(required=false)
    private JpaProperties jpaProperties;
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder, 
			@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
		return builder
				.dataSource(dynamicDataSource)
				.properties(jpaProperties.getProperties())
				.packages("com.thornBird.serviceModel")
				.build();
	}
	
	@Bean(name = "entityManager")
	public EntityManager entityManager(
			EntityManagerFactoryBuilder builder, 
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return entityManagerFactory.getObject().createEntityManager();
    }
	
	@Bean(name = "jpaTransactionManager")
	public PlatformTransactionManager transactionManager(
			EntityManagerFactoryBuilder builder, 
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory.getObject());
	}
}
