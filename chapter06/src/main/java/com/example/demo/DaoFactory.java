package com.example.demo;

import com.example.demo.users.SimpleSqlService;
import com.example.demo.users.SqlService;
import com.example.demo.users.UserDao;
import com.example.demo.users.UserDaoJdbc;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DaoFactory {

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
    public UserDao userDao(SqlService sqlService) {
        return new UserDaoJdbc(dataSource(), sqlService);
    }

    @Bean
    public SqlService sqlService() {
        HashMap<String, String> sqlMap = new HashMap<>();
        sqlMap.put("userAdd", "insert into users(id, name, password) values(?, ?, ?)");
        sqlMap.put("userGet", "select * from users where id = ?");
        sqlMap.put("userGetAll", "select * from users order by id");
        sqlMap.put("userDeleteAll", "delete from users");
        sqlMap.put("userGetCount", "select count(*) from users");

        return new SimpleSqlService(sqlMap);
    }
}
