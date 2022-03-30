package com.gestiondelivres.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Book(
    var id: String="",
    val title: String="",
    val author: String="",
    val image: String="",
    var read: Boolean=false,
    val description: String="",
) : Parcelable
