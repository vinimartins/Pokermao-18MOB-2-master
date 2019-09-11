package br.com.fiap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val number: String,
    val name: String,
    val imageURL: String,
    val ps:Int,
    val attack: Int,
    val defense:Int,
    val velocity:Int
): Parcelable
