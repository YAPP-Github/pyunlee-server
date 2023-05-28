dependencies {
    api(project(":cvs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.seleniumhq.selenium:selenium-java:4.1.3")
    implementation("io.github.bonigarcia:webdrivermanager:5.1.0")
    implementation("org.jsoup:jsoup:1.16.1")
    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
