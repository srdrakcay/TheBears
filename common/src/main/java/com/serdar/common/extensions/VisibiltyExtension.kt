package com.serdar.common.extensions

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.notShow() {
    visibility = View.GONE
}