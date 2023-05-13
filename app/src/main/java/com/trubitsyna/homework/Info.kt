package com.trubitsyna.homework

data class Info(
    val name: String,
    val surname: String,
    val age: Int
) {
    override fun toString(): String {
        return "Name = $name Surname = $surname Age = $age"
    }
}
