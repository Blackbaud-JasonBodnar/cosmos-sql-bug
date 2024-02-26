package com.example.cosmossqlbug

import com.example.cosmossqlbug.entity.TestEntity
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@ComponentTest
class TestEntityRepositorySpec extends Specification {
    @Autowired
    TestEntityRepository repository

    def "Should save and load BigDecimal correctly"() {
        given:
        TestEntity testEntity = TestEntity.builder()
                .amount(new BigDecimal("345.60"))
                .environmentId(UUID.randomUUID().toString())
                .build()

        when:
        repository.save(testEntity)

        then:
        TestEntity fromDb = repository.findById(testEntity.id).get()
        //noinspection ChangeToOperator - BigDecimal.equals() takes into account scale which is lost and what this test demonstrates
        assert fromDb.amount.equals(testEntity.amount)
    }

    def "Should set created date for all items in collection"() {
        given:
        TestEntity testEntity = TestEntity.builder()
                .amount(new BigDecimal("345.60"))
                .environmentId(UUID.randomUUID().toString())
                .build()

        List<TestEntity> entities = [].withEagerDefault {
            TestEntity.builder()
                .amount(BigDecimal.valueOf(it))
                .environmentId(UUID.randomUUID().toString())
                .build()
        }[0..4]

        when:
        testEntity = repository.save(testEntity)
        repository.saveAll(entities)

        then:
        TestEntity entityWithCreatedDate = repository.findById(testEntity.id).get()
        assert entityWithCreatedDate.createdDate != null
        repository.findAll().each {
            assert it.createdDate != null
        }
    }

    def cleanup() {
        repository.deleteAll()
    }
}
