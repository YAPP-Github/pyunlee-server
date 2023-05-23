import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.11" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("jacoco")
    id("org.sonarqube") version "3.5.0.2730"
    id("com.diffplug.spotless") version  "6.18.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

buildscript {
    repositories {
        mavenCentral()
    }
}


allprojects {
    repositories {
        mavenCentral()
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target ("**/*.kt")
        ktlint("0.48.0")
    }
    // ktlint -> no wild card import 구문 관련 설정
    // https://blog.leocat.kr/notes/2020/12/14/intellij-avoid-wildcard-imports-in-kotlin-with-intellij
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.sonarqube")
    apply(plugin = "jacoco")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        runtimeOnly("io.github.microutils:kotlin-logging-jvm:3.0.5")//logger

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    }

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    group = "com.yapp"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
    }

    sonarqube {
        properties {
            // 각 프로젝트마다 적용해야하는부분.
            property("sonar.java.binaries", "${buildDir}/classes")
            property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco.xml")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.getByName<Jar>("jar") {
        enabled = false
    }

    tasks.test {
        useJUnitPlatform()
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }

    val qDomains = mutableListOf<String>()
    for (qPattern in listOf("**/QA".."**/QZ")) {
        qDomains.add("$qPattern*")
    }

    // jacoco ci
    tasks.jacocoTestReport {
        dependsOn("test")
        reports {
            html.required.set(true)
            csv.required.set(true)
            xml.required.set(true)
            xml.outputLocation.set(File("${buildDir}/reports/jacoco.xml"))
        }
        finalizedBy("jacocoTestCoverageVerification")

        classDirectories.setFrom(
                files(classDirectories.files.map {
                    fileTree(it) {
                        exclude("**/*Application*",
                                "**/*Config*",
                                "**/*Dto*",
                                "**/*Request*",
                                "**/*Response*",
                                "**/*Interceptor*",
                                "**/*Exception*" ,
                                *qDomains.toTypedArray())
                    }
                })
        )
    }

    tasks.jacocoTestCoverageVerification {
        violationRules {
            rule {
                element = "CLASS"

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = "0.0".toBigDecimal()
                }
                excludes = listOf(
                    "**/*Application*",
                    "**/*Config*",
                    "**/*Dto*",
                    "**/*Request*",
                    "**/*Response*",
                    "**/*Interceptor*",
                    "**/*Exception*",
                    *qDomains.toTypedArray()
                )
            }
        }
    }

    sonarqube {
        properties {
            property("sonar.java.binaries", "${buildDir}/classes")
            property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco.xml")
        }
    }
}

jacoco {
    toolVersion = "0.8.7"
}

sonarqube {
    properties {
        property("sonar.projectKey", "YAPP-Github_22nd-Android-Team-1-BE")
        property("sonar.organization", "yapp-github")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sources", "src")
        property("sonar.language", "Kotlin")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.test.inclusions", "**/*Test.java")
        property("sonar.exclusions", "**/test/**, **/Q*.kt, **/*Doc*.kt, **/resources/** ,**/*Application*.kt , **/*Config*.kt, **/*Dto*.kt, **/*Request*.kt, **/*Response*.kt ,**/*Exception*.kt ,**/*ErrorCode*.kt")
        property("sonar.java.coveragePlugin", "jacoco")
    }
}
