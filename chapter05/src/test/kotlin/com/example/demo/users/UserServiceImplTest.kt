package com.example.demo.users

import com.example.demo.users.UserServiceImpl.Companion.WIN_LOGCOUNT_FOR_SILVER
import com.example.demo.users.UserServiceImpl.Companion.WIN_RECCOMEND_FOR_GOLD
import com.example.demo.users.UserServiceImpl.TestUserServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.MailSender

@SpringBootTest
internal class UserServiceImplTest {

    @Autowired
    val userServiceImpl: UserServiceImpl? = null

    @Autowired
    val mailSender: MailSender = DummyMailSender()

    @Autowired
    val userRepository: UserRepository? = null

    var users: MutableList<User> = mutableListOf()

    @BeforeEach
    fun setUp() {
        users = mutableListOf(
            User("bumjin", "박범진", "p1", Level.BASIC, WIN_LOGCOUNT_FOR_SILVER - 1, 0, "test1@test.com"),
            User("joytouch", "강명성", "p2", Level.BASIC, WIN_LOGCOUNT_FOR_SILVER, 0, "test2@test.com"),
            User("erwins", "신승환", "p3", Level.SILVER, 60, WIN_RECCOMEND_FOR_GOLD - 1, "test3@test.com"),
            User("madnite1", "이상호", "p4", Level.SILVER, 60, WIN_RECCOMEND_FOR_GOLD, "test4@test.com"),
            User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "test5@test.com")
        )
    }

    @Test
    fun bean() {
        Assertions.assertNotNull(userServiceImpl)
    }

    @Test
    fun upgradeLevels() {
        userServiceImpl!!.deleteAll()

        for (user in users) {
            userServiceImpl!!.add(user)
        }

        userServiceImpl!!.upgradeLevels()

        checkLevelUpgraded(users[0], false)
        checkLevelUpgraded(users[1], true)
        checkLevelUpgraded(users[2], false)
        checkLevelUpgraded(users[3], true)
        checkLevelUpgraded(users[4], false)
    }

    @Test
    fun add() {
        userServiceImpl!!.deleteAll()

        val userWithLevel = users[4]
        val userWithoutLevel = users[0]
        userWithoutLevel.level = null

        userServiceImpl!!.add(userWithLevel)
        userServiceImpl!!.add(userWithoutLevel)

        val userWithLevelRead = userServiceImpl!!.get(userWithLevel.id)
        val userWithoutLevelRead = userServiceImpl!!.get(userWithoutLevel.id)

        assertEquals(userWithLevelRead.level, userWithLevel.level)
        assertEquals(userWithoutLevelRead.level, Level.BASIC)
    }

    @Test
    fun upgradeAllOrNothing() {
        val testUserService: TestUserServiceImpl = TestUserServiceImpl(userServiceImpl!!.userRepository, users[3].id!!, mailSender)

        for (user in users) {
            testUserService.add(user)
        }

        try {
            testUserService.upgradeLevels()
        } catch (e: TestUserServiceException) {
        }

        checkLevelUpgraded(users[1], false)
    }

    private fun checkLevelUpgraded(user: User, upgraded: Boolean) {
        val userUpdate = userServiceImpl!!.get(user.id)
        if (upgraded) {
            assertEquals(userUpdate.level, user.level!!.next)
        } else {
            assertEquals(userUpdate.level, user.level)
        }
    }


}