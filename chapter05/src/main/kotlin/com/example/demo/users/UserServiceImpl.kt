package com.example.demo.users

import com.example.demo.users.Level.*
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(val userRepository: UserRepository, val mailSender: MailSender): UserService {

    class TestUserServiceImpl(userRepository: UserRepository, val id: String,
                              mailSender: MailSender
    ): UserServiceImpl(userRepository, mailSender) {
        @Transactional
        override fun upgradeLevel(user: User) {
            if (user.id.equals(id)) throw TestUserServiceException()
            super.upgradeLevel(user)
        }
    }

    companion object {
        const val WIN_LOGCOUNT_FOR_SILVER = 50
        const val WIN_RECCOMEND_FOR_GOLD = 30
    }

    override fun add(user: User) {
        if (user.level == null) {
            user.level = BASIC
        }

        userRepository.save(user)
    }

    override fun get(id: String?): User {
        return userRepository.findById(id!!).get()
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun deleteAll() {
        return userRepository.deleteAll()
    }

    override fun getCount(): Int {
        return userRepository.count().toInt()
    }

    fun upgradeLevels() {
        val users = getAll()
        for (user in users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user)
            }
        }
    }

    protected fun upgradeLevel(user: User) {
        user.upgradeLevel()
        userRepository.save(user)
        sendUpgradeEmail(user)
    }

    private fun sendUpgradeEmail(user: User) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.setFrom("useradmin@kusg.org")
        mailMessage.setSubject("Upgrade 안내")
        mailMessage.setText("사용자님의 등급이 " + user.level!!.name)

        mailSender.send(mailMessage)
    }

    private fun canUpgradeLevel(user: User): Boolean {
        return when(val currentLevel = user.level) {
            BASIC -> (user.login >= WIN_LOGCOUNT_FOR_SILVER)
            SILVER -> (user.recommend >= WIN_RECCOMEND_FOR_GOLD)
            GOLD -> false
            else -> throw IllegalArgumentException("Unknown Level: $currentLevel")
        }

    }
}