import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    application
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version "1.5.20"

    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("org.openapi.generator") version "5.3.0"
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

group = "ru.otus.highload"
version = "1.0.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    //Logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.23")
    runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.2")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.8")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.8")
}

tasks.test {
    useJUnitPlatform()
}

springBoot {
    buildInfo()
}
