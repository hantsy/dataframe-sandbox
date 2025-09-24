import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.testLogger)
    alias(libs.plugins.kotlinPluginDataframe)
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.kotlinSupport)
    implementation(libs.logback)
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

testlogger {
    // pick a theme - mocha, standard, plain, mocha-parallel, standard-parallel or plain-parallel
    theme = MOCHA

    // set to false to disable detailed failure logs
    showExceptions = true

    // set to false to hide stack traces
    showStackTraces = true

    // set to true to remove any filtering applied to stack traces
    showFullStackTraces = false

    // set to false to hide exception causes
    showCauses = true

    // set threshold in milliseconds to highlight slow tests
    slowThreshold = 2000

    // displays a breakdown of passes, failures and skips along with total duration
    showSummary = true

    // set to true to see simple class names
    showSimpleNames = false

    // set to false to hide passed tests
    showPassed = true

    // set to false to hide skipped tests
    showSkipped = true

    // set to false to hide failed tests
    showFailed = true

    // enable to see standard out and error streams inline with the test results
    showStandardStreams = false

    // set to false to hide passed standard out and error streams
    showPassedStandardStreams = true

    // set to false to hide skipped standard out and error streams
    showSkippedStandardStreams = true

    // set to false to hide failed standard out and error streams
    showFailedStandardStreams = true

    logLevel = LogLevel.LIFECYCLE
}
