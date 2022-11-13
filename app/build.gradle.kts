plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    application
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val version = "0.7.36"
val target = "linux-x64"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("org.jetbrains.skiko:skiko:$version")
    implementation("org.jetbrains.skiko:skiko-awt:$version")
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.3.3")
    implementation("it.unimi.dsi:fastutil:8.2.1")

    implementation(fileTree("lib") {
        include("*.jar")
    })
}

application {
    // Make graphics module stuff available runtime
    applicationDefaultJvmArgs = listOf(
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-exports", "java.desktop/sun.java2d=ALL-UNNAMED",
        "--add-exports", "java.desktop/sun.awt=ALL-UNNAMED",
        "--add-opens", "java.desktop/sun.awt.X11=ALL-UNNAMED",
    )
    mainClass.set("fleetui.AppKt")
}

// Fleet is built with 17, must be the same
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "17" }
