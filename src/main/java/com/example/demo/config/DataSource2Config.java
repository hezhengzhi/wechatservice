package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by pc on 2018/12/17 14:00
 **/
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.source2",
        sqlSessionTemplateRef = "source2SqlSessionTemplate")
public class DataSource2Config {
    @Bean(name = "source2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.source2")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "source2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("source2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/source2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "source2TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("source2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "source2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("source2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
