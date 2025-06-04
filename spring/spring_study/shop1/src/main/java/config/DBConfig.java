package config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration //2개의 Configuration도 가능하다.
public class DBConfig { 
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		// ds : connection 객체 : 기존 방식(database connection)을 대체
		ComboPooledDataSource ds = new ComboPooledDataSource(); //커넥션 풀 객체
		try {
			ds.setDriverClass("org.mariadb.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mariadb://localhost:3306/gdjdb");
			ds.setUser("gduser");
			ds.setPassword("1234");
			ds.setMaxPoolSize(20); //최대 객체의 갯수
			ds.setMinPoolSize(3); //최소 객체의 갯수
			ds.setInitialPoolSize(5); //초기화 객체의 갯수
			ds.setAcquireIncrement(5); //증가 단위
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return ds;
	}
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource()); //위의 Bean을 호출
		bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return bean.getObject();
	}
	@Bean // 객체화됨
	public SqlSessionTemplate sqlSessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory());
	}
}
