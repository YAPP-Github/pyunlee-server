dependencies {
    api(project(":cvs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jsoup:jsoup:1.15.4")
    implementation ("org.seleniumhq.selenium:selenium-java:3.141.59")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.batch:spring-batch-test")
    testRuntimeOnly("com.h2database:h2")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}