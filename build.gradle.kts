import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.25"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    id("org.springframework.boot") version "3.4.0" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
}
val springCloudVersion = "2023.0.1"

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("groovy")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("org.spockframework:spock-core:2.4-M4-groovy-4.0")
        testImplementation("org.spockframework:spock-spring:2.4-M4-groovy-4.0")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    tasks.withType<JavaCompile>{
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    apply(plugin = "org.jetbrains.kotlin.plugin.spring" )
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa" )
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

subprojects {
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }

    tasks.findByName("bootJar")?.apply {
        enabled = false
    }

    tasks.findByName("jar")?.apply {
        enabled = true
    }
}