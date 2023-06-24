package com.kampus.merdeka.realtimedb

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TireModel(
    var name: String,
    var price: String,
    var rate: Int,
    var description: String
) : Parcelable