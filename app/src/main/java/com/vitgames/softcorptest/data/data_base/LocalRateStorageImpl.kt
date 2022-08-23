package com.vitgames.softcorptest.data.data_base

import com.vitgames.softcorptest.domain.LocalRateStorage
import javax.inject.Inject

class LocalRateStorageImpl @Inject constructor(dataBase: RateMainDataBase) : LocalRateStorage {

    private val db = dataBase.rateDao()

    override fun getAll(): List<RateEntity> = db.getAll()

    override fun insert(entity: RateEntity) = db.insert(entity)

    override fun delete(entity: RateEntity) = db.delete(entity)
}