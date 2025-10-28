package com.halimjr11.locaroo.common

fun Int?.orZero(): Int = this ?: 0
fun Long?.orLongZero(): Long = this ?: 0
fun Double?.orDoubleZero(): Double = this ?: 0.0