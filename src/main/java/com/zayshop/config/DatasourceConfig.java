package com.zayshop.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zayshop.service.EnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DatasourceConfig {

    @Autowired
    private EnvService envService;
    //config bang @Configuration phai tro dung bean
    @Bean("dataSource")
    public DataSource getDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(envService.getEnvValue("DB_CLASSNAME"));
        config.setJdbcUrl(envService.getEnvValue("DB_URL"));
        config.setUsername(envService.getEnvValue("DB_USERNAME"));
        config.setPassword(envService.getEnvValue("DB_PASSWORD"));
        return  new HikariDataSource(config);
    }
}
