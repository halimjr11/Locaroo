package com.halimjr11.locaroo.common

/**
 * Returns this value if it is not null, otherwise returns 0.
 *
 * @return the value of this nullable integer, or 0 if it is null.
 */
fun Int?.orZero(): Int = this ?: 0

/**
 * Returns this value if it is not null, otherwise returns 0.
 *
 * @return the value of this nullable long, or 0 if it is null.
 */
fun Long?.orLongZero(): Long = this ?: 0

/**
 * Returns this value if it is not null, otherwise returns 0.0.
 *
 * @return the value of this nullable double, or 0.0 if it is null.
 */
fun Double?.orDoubleZero(): Double = this ?: 0.0
