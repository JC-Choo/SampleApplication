package dev.chu.memo.etc.extension

val Any.TAG: String
    get() = this::class.simpleName ?: this.toString()