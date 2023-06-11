dependencies {
    api(project(":cvs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.json:json:20210307")
    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
