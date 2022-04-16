package com.example.demo.users

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class DummyMailSender: MailSender {

    override fun send(simpleMessage: SimpleMailMessage) {
        TODO("Not yet implemented")
    }

    override fun send(vararg simpleMessages: SimpleMailMessage?) {
        TODO("Not yet implemented")
    }
}