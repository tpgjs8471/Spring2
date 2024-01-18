package com.myweb.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@MapperScan(basePackages = {"com.myweb.www.repository"})
@ComponentScan(basePackages = {"com.myweb.www.service","com.myweb.www.exception"})
@EnableTransactionManagement
@EnableScheduling
@Configuration
public class RootConfig {
	// DB설정부분
	// hikariCp 사용 / log4jdbc-log4j2 사용
	
	// @autowired :  객체 자동 생성 // inject ?
	@Autowired
	ApplicationContext applicationContext;
	
	// @bean : web.xml 에서의 bean 과 동일
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		// log4jdbc-log4j2 : Log4jdbc라는 JDBC 드라이버로, 데이터베이스와 상호 작용하는 SQL 쿼리를 로깅하고 모니터링하는 데 사용
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/mywebdb");
		hikariConfig.setUsername("mywebUser");
		hikariConfig.setPassword("mysql");
		// 위 4개는 필수적으로 있어야하는 구문
		
		hikariConfig.setMaximumPoolSize(5); // 최대 커넥션 개수
		hikariConfig.setMinimumIdle(5); // 최소 유휴 커넥션 개수(비어있을 수 있는 개수) / 위의 값과 같은 값으로 설정해야 함
		
		hikariConfig.setConnectionTestQuery("SELECT NOW()"); // test용 쿼리문
		hikariConfig.setPoolName("springHikariCP");
		
		// 추가 설정
		// cachePrepStmts : cache 사용 여부 설정 , JDBC에서 제공하는 API
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", true); // (name,value)
		// mysql 드라이버가 연결당 cache statement의 수에 관한 설정
		// 일반적으로 250~500 사이를 권장함
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", 250);
		// connection 당 캐싱할 preparedStatement 개수 지정 옵션 : default 256
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit","true"); // "true" : 기본값으로 설정
		// 위 3개는 함께 다닌다고 생각
		// mysql 서버에서최신 이슈가 있을 경우 지원받는 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
		
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSource());
		sqlFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mappers/*.xml"));
		
		sqlFactoryBean.setConfigLocation(
				applicationContext.getResource("classpath:/MybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
	
	}
	
	// 트렌제션 메니저 빈 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
