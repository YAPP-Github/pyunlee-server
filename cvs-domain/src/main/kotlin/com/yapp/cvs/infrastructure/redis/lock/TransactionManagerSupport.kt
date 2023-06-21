package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.exception.InvalidLockException
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.support.TransactionSynchronizationManager

@Component
class TransactionManagerSupport(
        private val transactionManager: PlatformTransactionManager
) {
    // 쓰레드별 반드시 하나의 트랜잭션 실행을 보장한다
    fun execute(operation: () -> Unit) {
        this.validatePreExistingTransaction()
        val transaction = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            TransactionSynchronizationManager.bindResource(transactionManager, transaction)
            operation()
            transactionManager.commit(transaction)
        } catch (e: Exception) {
            transactionManager.rollback(transaction)
            throw e
        } finally {
            TransactionSynchronizationManager.unbindResource(transactionManager)
        }
    }

    private fun validatePreExistingTransaction() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            throw InvalidLockException("선행 트랜잭션이 이미 존재합니다.")
        }
    }
}
