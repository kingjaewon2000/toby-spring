package com.example.demo

import com.example.demo.users.DummyMailSender
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class DaoFactory {

    @Bean
    fun mailSender(): MailSender {
        val mailSender = DummyMailSender()
//        mailSender.host = "mail.server.com"

        return mailSender
    }
}