// Based on "gradle init"

plugins {
    // Fleet is built with higher kotlin version than default from "gradle init" - updated to latest
    kotlin("jvm").version("1.7.20")
    application
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val version = "0.7.9"
val target = "linux-x64"

// Set this to actual location of Fleet. YMMV
val fleetInstallDir = "${System.getProperty("user.home")}/.local/share/JetBrains/Toolbox/apps/Fleet/ch-0/1.9.237"

dependencies {
    // This is added manually, hopefully it will override any skikio in fleet-folder
    implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$version")

    // From "gradle init"
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:31.0.1-jre")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    implementation(fileTree(fleetInstallDir) {
        include("**/*.jar")
    })
}

application {
    // Make graphics module stuff available runtime
    applicationDefaultJvmArgs = listOf(
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-exports", "java.desktop/sun.java2d=ALL-UNNAMED",
        "--add-exports", "java.desktop/sun.awt=ALL-UNNAMED",
        "--add-opens", "java.desktop/sun.awt.X11=ALL-UNNAMED"
    )
    mainClass.set("flowui.AppKt")
}

// Fleet is built with 17, must be the same
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "17" }

// Lots of duplicate jar-files in fleet folder
// Filtering out duplicates instead of picking the actually required jar-files. Long live lazyness
gradle.taskGraph.whenReady {
    allTasks
        .filter { it.hasProperty("duplicatesStrategy") }
        .forEach {
            it.setProperty("duplicatesStrategy", "EXCLUDE")
        }
}
