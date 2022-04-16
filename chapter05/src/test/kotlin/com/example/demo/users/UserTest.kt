package com.example.demo.users

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UserTest {

    var user: User? = null;

    @BeforeEach
    fun setUp() {
        user = User()
    }

    @Test
    fun upgradeLevel() {
        val levels = Level.values()
        for (level in levels) {
            if (level.next == null) continue
            user!!.level = level
            user!!.upgradeLevel()
            assertEquals(user!!.level, level.next)
        }
    }

    @Test
    fun cannotUpgradeLevel() {
        assertThrows(IllegalStateException::class.java) {
            val levels = Level.values()
            for (level in levels) {
                if (level.next != null) continue
                user!!.level = level
                user!!.upgradeLevel()
            }
        }
    }
}