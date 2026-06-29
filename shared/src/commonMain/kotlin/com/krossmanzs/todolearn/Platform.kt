package com.krossmanzs.todolearn

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform