package com.jlara.ejemplorvcv_kotlin

import android.view.View
import android.widget.ImageView

interface OnItemClickListener {
    fun onItemClick(vista: View, position:Int)
    fun onImageClick(view: View, position: Int)
}