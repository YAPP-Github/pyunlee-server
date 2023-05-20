dependencies {
    implementation(project(":cvs-domain"))

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
    implementation("org.springframework.boot:spring-boot-starter-security")
}