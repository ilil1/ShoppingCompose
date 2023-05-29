package com.example.domain.model

data class Product(
    val productId : String,
    val productName : String,
    val ImageUrl : String,
    val price : Price,
    val category: Category,
    val shop : Shop,
    val inNew : Boolean,
    val isFreeShipping : Boolean
)
