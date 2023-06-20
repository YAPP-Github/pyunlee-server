package com.yapp.cvs.infrastructure.redis.lock

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong


class ConcurrentExecutionSupport {
    companion object {
        private const val numberOfThreads = 100
        private const val numberOfThreadPool = 10

        fun execute(operation: () -> Any?, successCount: AtomicLong) {
            val executorService = Executors.newFixedThreadPool(numberOfThreadPool)
            val latch = CountDownLatch(numberOfThreads)
            for (i in 1..numberOfThreads) {
                executorService.submit {
                    try {
                        operation()
                        successCount.getAndIncrement()
                    }
                    catch(e: Throwable) {
                        println(e)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
        }
    }
}