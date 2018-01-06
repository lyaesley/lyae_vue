package com.lyae;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 데이터베이스 접속 설정
 * 2017-04-19 박용서 작성
 */
@Configuration
@EnableTransactionManagement
public class DBConfiguration {
	
	// =====================================================================================
	// - lyae 접속 설정
	// =====================================================================================
	
	@Bean(name = "lyaeDataSource") @ConfigurationProperties(prefix = "db.lyae") 
	public DataSource lyaeDataSource() { 
//		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		return DataSourceBuilder.create().build(); 
	}


	
	@Bean(name = "lyaeSqlSessionFactoryBean")
	public SqlSessionFactoryBean tssSessionFactoryBean(@Qualifier("lyaeDataSource") DataSource lyaeDataSource, ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(lyaeDataSource);
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/**.xml"));
		
		org.apache.ibatis.session.Configuration conf = factoryBean.getObject().getConfiguration();
		conf.setCacheEnabled(true);
		conf.setLazyLoadingEnabled(false);
		conf.setMultipleResultSetsEnabled(true);
		conf.setUseGeneratedKeys(false);
		conf.setDefaultExecutorType(ExecutorType.BATCH);
		
		return factoryBean;
	}
	
	@Bean(name = "tssSqlSessionTemplate")
    public SqlSessionTemplate lyaeSqlSessionTemplate(@Qualifier("lyaeSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
