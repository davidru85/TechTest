package com.ruizurraca.techtest.utils.extensions

import android.view.View

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.isVisible() = (this?.visibility == View.VISIBLE)

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.isGone() = (this?.visibility == View.GONE)