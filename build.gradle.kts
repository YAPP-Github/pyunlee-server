import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "com.yapp"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring") //all-open
    apply(plugin = "kotlin-jpa")

    dependencies {
        // springboot
        implementation("org.springframework.boot:spring-boot-starter-batch")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
//        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        developmentOnly("org.springframework.boot:spring-boot-devtools")

        // open feign
//        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        //lombok
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        // DB
        runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.batch:spring-batch-test")
        testImplementation("org.springframework.security:spring-security-test")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            dependency("net.logstash.logback:logstash-logback-encoder:6.6")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
}

// module core 에 module api, consumer이 의존
project(":cvs-api") {
    dependencies {
        implementation(project(":cvs-domain"))
    }
}

project(":cvs-batch") {
    dependencies {
        implementation(project(":cvs-domain"))
    }
}

// core 설정
project(":cvs-domain") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

}