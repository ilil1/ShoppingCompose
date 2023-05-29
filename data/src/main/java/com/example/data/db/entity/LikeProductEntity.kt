package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.BasketConverter
import com.example.data.db.converter.LikeConverter
import com.example.domain.model.Category
import com.example.domain.model.Price
import com.example.domain.model.Product
import com.example.domain.model.Shop

@Entity(tableName = "Like")
@TypeConverters(LikeConverter::class)
data class LikeProductEntity(
    @PrimaryKey
    val productId : String,
    val productName : String,
    val ImageUrl : String,
    val price : Price,
    val category: Category,
    val shop : Shop,
    val inNew : Boolean,
    val isFreeShipping : Boolean
)

fun LikeProductEntity.toDomainModel(): Product {
    return Product(
        productId = productId,
        productName = productName,
        ImageUrl = ImageUrl,
        price = price,
        category = category,
        shop = shop,
        inNew = inNew,
        isFreeShipping = isFreeShipping
    )
}