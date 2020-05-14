package dev.chu.memo.ui.recycler_multi_viewtype.etc

fun <T> lazyN(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)