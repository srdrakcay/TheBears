package com.serdar.common.extensions

fun String.combineString(mainString: String): String {
    return "$this $mainString"
}