package com.yapp.cvs.infrastructure.redis

import com.yapp.cvs.DomainIntegrationTest
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.IllegalTransactionStateException
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicLong

@DomainIntegrationTest
class DistributedLockAopTest {
    @Autowired
    private lateinit var recommendService: RecommendService

    @Service
    class RecommendService(val anotherService: AnotherService) {
        var status: Boolean = false

        @Transactional
        @DistributedLock(DistributedLockType.PRODUCT_LIKE, ["productId"])
        fun recommendByIdWithLock(productId: Long) {
            execute()
        }

        @Transactional
        @DistributedLock(DistributedLockType.PRODUCT_LIKE, ["productId", "memberId"])
        fun recommendByCompositeKeyWithLock(productId: Long, memberId: Long) {
            execute()
        }

        @Transactional
        fun callAnotherServiceMethod(productId: Long) {
            anotherService.anotherMethod(productId)
        }

        fun recommendByIdWithoutLock(productId: Long) {
            execute()
        }

        private fun execute() {
            if(!status) {
                status = true
            } else {
                throw RuntimeException("이미 추천한 상품입니다.")
            }
        }
    }

    @Service
    class AnotherService {
        // 내부 메소드 호출은 트랜잭션 적용되지 않아 별도의 서비스 클래스 선언
        @Transactional
        @DistributedLock(DistributedLockType.PRODUCT_LIKE, ["anotherId"])
        fun anotherMethod(anotherId: Long) {}
    }

    @BeforeEach
    fun setUp() {
        recommendService.status = false
    }

    @Test
    @DisplayName("분산락 적용시 추천은 한번만 이루어져야 한다")
    fun distributedLockTest() {
        // when
        val successCount = AtomicLong()
        ConcurrentExecutionSupport.execute(
                { recommendService.recommendByIdWithLock(1L) },
                successCount,
        )
        // then
        println(successCount.toLong())
        assertThat(successCount.toLong()).isEqualTo(1L)
    }

    @Test
    @DisplayName("복합키에도 분산락이 적용되어야 한다")
    fun distributedLockWithCompositeKeyTest() {
        // when
        val successCount = AtomicLong()
        ConcurrentExecutionSupport.execute(
                { recommendService.recommendByCompositeKeyWithLock(1L, 3L) },
                successCount,
        )
        // then
        println(successCount.toLong())
        assertThat(successCount.toLong()).isEqualTo(1L)
    }

    @Test
    @DisplayName("선행 트랜잭션 내부에서 Lock 연산이 발생하면 안된다")
    fun transactionalFailTest() {
        // then
        assertThrows<IllegalTransactionStateException> {
            recommendService.callAnotherServiceMethod(1L)
        }
    }

    @Test
    @DisplayName("분산락 미적용 시 동시성 이슈가 발생할수도 있다")
    fun distributedLockNotUsedTest() {
        // when
        val successCount = AtomicLong()
        ConcurrentExecutionSupport.execute(
                { recommendService.recommendByIdWithoutLock(1L) },
                successCount,
        )
        // then
        println(successCount.toLong())
        assertThat(successCount.toLong()).isGreaterThanOrEqualTo(1L)
    }
}
