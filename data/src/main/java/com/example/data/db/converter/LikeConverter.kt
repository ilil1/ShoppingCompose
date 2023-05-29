package com.example.data.db.converter

import androidx.room.TypeConverter
import com.example.domain.model.Category
import com.example.domain.model.Price
import com.example.domain.model.Shop
import com.google.gson.GsonBuilder

class LikeConverter {

    private val gson = GsonBuilder().create()

    //Price 객체를 Json 으로 반환
    @TypeConverter
    fun fromPrice(value : Price) : String {
        return gson.toJson(value)
    }

    //String 형태의 Json 을 Price Gson 으로 반환
    @TypeConverter
    fun toPrice(value : String) : Price {
        return gson.fromJson(value, Price::class.java)
    }

    //객체를 Json 으로 반환
    @TypeConverter
    fun fromCategory(value : Category) : String {
        return gson.toJson(value)
    }

    //String 형태의 Json 을 Gson 으로 반환
    @TypeConverter
    fun toCategory(value : String) : Category {
        return gson.fromJson(value, Category::class.java)
    }

    //객체를 Json 으로 반환
    @TypeConverter
    fun fromShop(value : Shop) : String {
        return gson.toJson(value)
    }

    //String 형태의 Json 을 Gson 으로 반환
    @TypeConverter
    fun toShop(value : String) : Shop {
        return gson.fromJson(value, Shop::class.java)
    }
}