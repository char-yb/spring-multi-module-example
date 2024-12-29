import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.kotlin.jpa) apply false
}

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
}

subprojects {
    val libs = rootProject.libs
    fun getPlugin(provider: Provider<PluginDependency>): String = provider.get().pluginId

    apply(plugin = getPlugin(libs.plugins.kotlin.jvm))
    apply(plugin = getPlugin(libs.plugins.kotlin.spring))
    apply(plugin = getPlugin(libs.plugins.kotlin.jpa))
    apply(plugin = getPlugin(libs.plugins.spring.boot))
    apply(plugin = getPlugin(libs.plugins.spring.dependency.management))

    java {
        sourceCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        testImplementation(libs.spring.mockk)
        testImplementation(libs.bundles.kotest)
        testImplementation(libs.spring.boot.starter.test)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}