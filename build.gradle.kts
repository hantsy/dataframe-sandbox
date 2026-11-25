import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.dependencyManagement)
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinPluginSpring)
    // The DataFrame compiler plugin ships with Kotlin itself and replaces the former
    // KSP based "org.jetbrains.kotlinx.dataframe" Gradle plugin.
    alias(libs.plugins.kotlinPluginDataframe)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.testLogger)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot, without any web layer
    implementation(libs.bundles.spring)

    implementation(libs.bundles.kotlinSupport)

    // Kotlin DataFrame
    implementation(libs.bundles.dataframe)

    // Test libs
    testImplementation(libs.bundles.springtest)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.bundles.kotlintest)
    testRuntimeOnly(libs.junitPlatformLauncher)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
    compilerOptions {
        jvmDefault = JvmDefaultMode.NO_COMPATIBILITY
        progressiveMode.set(true)
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
        )
        optIn.addAll(
            "kotlin.RequiresOptIn",
            "kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    minHeapSize = "128m"
    maxHeapSize = "2048m"
}

tasks.withType<LintTask> {
    exclude { it.file.path.contains("build/generated") }
}

tasks.withType<FormatTask> {
    exclude { it.file.path.contains("build/generated") }
}

tasks.named<Jar>("jar") {
    // disable plain jar alongside the executable jar
    enabled = false
}

kotlinter {
    ignoreLintFailures = false
    reporters = arrayOf("checkstyle", "plain")
}

testlogger {
    theme = MOCHA
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = false
    showCauses = true
    slowThreshold = 2000
    showSummary = true
    showPassed = true
    showSkipped = true
    showFailed = true
    showStandardStreams = false
    logLevel = LogLevel.LIFECYCLE
}

springBoot {
    buildInfo()
}
