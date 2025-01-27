package com.yosmisyael.dlib.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val title: String,
    val description: String,
    val photo: Int,
    val author: String,
    val year: Int,
    val category: String,
    val link: String = "link book placeholder, soon will work :)"
) : Parcelable
