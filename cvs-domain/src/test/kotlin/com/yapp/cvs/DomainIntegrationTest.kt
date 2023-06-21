package com.yapp.cvs

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [DomainIntegrationTestConfiguration::class])
@ActiveProfiles("develop")
@MustBeDocumented
annotation class DomainIntegrationTest()
