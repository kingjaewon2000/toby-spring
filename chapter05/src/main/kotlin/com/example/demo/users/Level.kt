package com.example.demo.users

enum class Level(val value: Int, val next: Level?) {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);
}