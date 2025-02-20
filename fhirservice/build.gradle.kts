dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

    implementation("ca.uhn.hapi.fhir:hapi-fhir-base")
    implementation("ca.uhn.hapi.fhir:hapi-fhir-structures-r4")
    implementation("ca.uhn.hapi.fhir:hapi-fhir-client")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}