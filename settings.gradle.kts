pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "playground"

include("module-api")
include("module-clients")
include("module-common")
include("module-domain")
include("module-datasource")
include("module-test")