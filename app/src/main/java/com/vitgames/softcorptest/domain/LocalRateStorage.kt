package com.vitgames.softcorptest.domain

import com.vitgames.softcorptest.data.data_base.RateEntity

interface LocalRateStorage {
    fun getAll(): List<RateEntity>
    fun insert(entity: RateEntity)
    fun delete(entity: RateEntity)
}