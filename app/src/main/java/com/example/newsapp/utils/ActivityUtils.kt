package com.example.newsapp.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/*
Util Functions for clean lookup in codebase
* */
fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}