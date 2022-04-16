package com.example.demo.users

interface UserService {
    fun add(user: User)
    fun get(id: String?): User
    fun getAll(): List<User>
    fun deleteAll()
    fun getCount(): Int
}