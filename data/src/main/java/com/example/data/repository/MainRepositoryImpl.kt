package com.example.data.repository

import android.content.Context
import com.example.data.deserializer.BaseModelDeserializer
import com.example.domain.model.BaseModel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.repository.MainRepository
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    @ApplicationContext private val context : Context
) : MainRepository {
    override fun getModelList(): Flow<List<BaseModel>> = flow {

        val inputStream = context.assets.open("product_list")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonString = inputStreamReader.readText()
        val type = object : TypeToken<List<BaseModel>>() { }.type

        val gson = GsonBuilder()
            .registerTypeAdapter(BaseModel::class.java, BaseModelDeserializer())
            .create()

        val products: List<Product> = gson.fromJson(jsonString, type)

        emit(products)

        //emit(GsonBuilder().create().fromJson(jsonString, type))
    }
}

class CategoryTypeAdapter : JsonDeserializer<Category>, JsonSerializer<Category> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Category {
        val jsonObject = json.asJsonObject
        val categoryId = jsonObject.get("categoryId").asString
        val categoryName = jsonObject.get("categoryName").asString

        return when (categoryId) {
            "1" -> Category.Top
            "2" -> Category.Outerwear
            "3" -> Category.Dress
            "4" -> Category.Pants
            "5" -> Category.Skirt
            "6" -> Category.Shoes
            "7" -> Category.Bag
            "8" -> Category.FashionAccessories
            else -> throw IllegalArgumentException("Invalid category id: $categoryId")
        }
    }

    override fun serialize(src: Category, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("categoryId", src.categoryId)
        jsonObject.addProperty("categoryName", src.categoryName)
        return jsonObject
    }
}