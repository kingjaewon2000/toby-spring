package com.example.demo.users

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User {

    @Id
    var id: String? = null
    var name: String? = null
    var password: String? = null
    var level: Level? = Level.BASIC
    var login: Int = 0
    var recommend: Int = 0
    var email: String? = null

    constructor()

    constructor(id: String?, name: String?, password: String?) {
        this.id = id
        this.name = name
        this.password = password
    }

    constructor(
        id: String?,
        name: String?,
        password: String?,
        level: Level?,
        login: Int,
        recommend: Int,
        email: String?
    ) {
        this.id = id
        this.name = name
        this.password = password
        this.level = level
        this.login = login
        this.recommend = recommend
        this.email = email
    }


    fun upgradeLevel() {
        val nextLevel = this.level?.next

        if (nextLevel == null) {
            throw IllegalStateException("${this.level}은 업그레이드가 불가능합니다")
        } else {
            this.level = nextLevel
        }
    }


}