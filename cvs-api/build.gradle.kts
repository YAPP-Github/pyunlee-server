tasks.getByName<Jar>("jar") {
    enabled = false
}

dependencies {
    api(project(":cvs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
