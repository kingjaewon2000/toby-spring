package com.example.demo;

import com.example.demo.users.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public DConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

//   @Bean
//     public UserDao userDao() {
//         return new UserDao(connectionMaker());
//     }

//    public UserDao accountDao() {
//        return new AccountDao(connectionMaker());
//    }
//
//    public UserDao messageDao() {
//        return new MessageDao(connectionMaker());
//    }
}
