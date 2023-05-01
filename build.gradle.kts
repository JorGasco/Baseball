plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "me.jorgegasco"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("org.codehaus.jettison:jettison:1.5.4")
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(16)
}

application {
    mainClass.set("MainKt")
}