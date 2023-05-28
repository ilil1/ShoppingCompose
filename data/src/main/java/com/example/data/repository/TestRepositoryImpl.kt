package com.example.data.repository

import com.example.data.datasource.TestDataSource
import com.example.data.model.toDomainModel
import com.example.domain.model.TestModel
import com.example.domain.repository.TestRepository

//dataSource 에서 Mock data 를 넣어서 반환해서 그 data 를 DomainModel 로 반환
class TestRepositoryImpl(val dataSource: TestDataSource) : TestRepository {
    override fun getTestData(): TestModel {
        return dataSource.getTestModelResponse().toDomainModel()!!
    }
}

