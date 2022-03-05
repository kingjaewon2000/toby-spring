package com.example.demo;

import com.example.demo.users.UserDao;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/toby?serverTimezone=UTC&characterEncoding=UTF-8")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("root")
                .build();

        return dataSource;
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(dataSource());
    }
}
