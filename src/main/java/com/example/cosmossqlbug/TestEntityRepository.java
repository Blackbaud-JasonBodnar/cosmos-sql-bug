package com.example.cosmossqlbug;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.cosmossqlbug.entity.TestEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TestEntityRepository extends CosmosRepository<TestEntity, String> {
}
