package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.PurchaseProductEntity

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchase")
    suspend fun getAll() : List<PurchaseProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item : PurchaseProductEntity)

    @Query("DELETE FROM basket WHERE productId=:id")
    suspend fun delete(id : String)
}