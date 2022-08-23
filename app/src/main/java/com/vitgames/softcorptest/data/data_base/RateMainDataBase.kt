package com.vitgames.softcorptest.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RateEntity::class], version = 1)
abstract class RateMainDataBase : RoomDatabase() {
    abstract fun rateDao(): RateDao
}