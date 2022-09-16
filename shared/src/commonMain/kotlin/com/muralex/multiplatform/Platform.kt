package com.muralex.multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform