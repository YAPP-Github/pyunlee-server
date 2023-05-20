import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.11" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("com.diffplug.spotless") version  "6.18.0"
    id("org.sonarqube") version "3.5.0.2730"
    id("jacoco")
}

java.sourceCompatibility = JavaVersion.VERSION_17

sonarqube {
    properties {
        property("sonar.projectKey", "YAPP-Github_22nd-Android-Team-1-BE")
        property("sonar.organization", "yapp-github")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sources", "src")
        property("sonar.java.coveragePlugin", "jacoco")
    }
}

allprojects {
    group = "com.yapp"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        finalizedBy("jacocoTestReport")
    }

    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa" )
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        target ("**/*.kt")
        ktlint("0.48.0")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "jacoco" )


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

    tasks.getByName<Jar>("jar") {
        enabled = false
    }

    tasks.test {
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
                    minimum = "0.10".toBigDecimal()
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

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"

    dependsOn(":test",
            ":jacocoTestReport",
            ":jacocoTestCoverageVerification")

    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}