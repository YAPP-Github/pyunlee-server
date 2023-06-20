package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.PyunleeDomainApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [PyunleeDomainApplication::class])
class DomainIntegrationTestConfiguration