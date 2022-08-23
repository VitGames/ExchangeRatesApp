package com.vitgames.softcorptest.data.data_base

import androidx.room.*

@Dao
interface RateDao {
    @Query("SELECT * FROM RateEntity")
    fun getAll(): List<RateEntity>

    @Query("SELECT * FROM RateEntity WHERE rateId = :id")
    fun getById(id: String): RateEntity

    @Insert
    fun insert(entity: RateEntity)

    @Update
    fun update(entity: RateEntity)

    @Delete
    fun delete(entity: RateEntity)
}