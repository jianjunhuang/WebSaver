package com.jianjun.base.utils

fun String.isHttp(): Boolean {
    return startsWith("http") || startsWith("https")
}