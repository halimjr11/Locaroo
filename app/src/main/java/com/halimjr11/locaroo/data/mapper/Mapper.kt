package com.halimjr11.locaroo.data.mapper

interface Mapper<in T, out R> {
    fun map(input: T): R
}

interface TwoWayMapper<F, T> : Mapper<F, T> {
    fun reverseMap(input: T): F
}
