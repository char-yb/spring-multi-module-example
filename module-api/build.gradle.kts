tasks.findByName("bootJar")?.apply {
    enabled = true
}

dependencies {
    implementation(project(":module-domain"))
    implementation(project(":module-common"))
}