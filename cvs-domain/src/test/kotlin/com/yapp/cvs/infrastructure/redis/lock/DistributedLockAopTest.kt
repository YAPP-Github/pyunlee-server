package com.yapp.cvs.infrastructure.redis.lock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.atomic.AtomicLong

@SpringBootTest(classes = [DomainIntegrationTestConfiguration::class])
@ActiveProfiles("develop")
class DistributedLockAopTest {
    @Autowired
    lateinit var recommendService: RecommendService

    @Service
    class RecommendService(var recommendStatus: Boolean = false) {
        @DistributedLock("productId", "recommendLock")
        fun recommend(productId: Long) {
            if (!recommendStatus) {
                recommendStatus = true
            } else {
                throw RuntimeException("이미 추천한 상품")
            }
        }
    }

    @Test
    fun `분산락 적용시 추천은 한번만 이루어져야 한다`() {
        // given
        // when
        val successCount = AtomicLong()
        ConcurrentExecutionSupport.execute(
                { recommendService.recommend(1L) },
                successCount,
        )
        // then
        assertThat(successCount.toLong()).isEqualTo(1L)
    }
}
