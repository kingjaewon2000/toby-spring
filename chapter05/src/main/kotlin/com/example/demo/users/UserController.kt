package com.example.demo.users

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @GetMapping("{id}")
    fun user(@PathVariable id: String): User {
        return userService.get(id)
    }

    @GetMapping
    fun users(): List<User> {
        return userService.getAll()
    }

    @PostMapping
    fun addUser(@RequestBody dto: UserSaveDto): User {
        val user = User(dto.id, dto.name, dto.password)
        userService.add(user)

        return user
    }

    @DeleteMapping
    fun deleteAllUser() {
        return userService.deleteAll()
    }

    @GetMapping("/count")
    fun count(): Int {
        return userService.getCount()
    }
}