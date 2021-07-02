package com.example.Shoppies.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "products"
)
data class Products(
    val category: String?,
    val description: String?,
    @PrimaryKey
    val id: Int?,
    val image: String?,
    val price: Double?,
    val title: String?,
    var amt:String?
):Serializable