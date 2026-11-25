# dataframe-sandbox

Kotlinx DataFrame Sandbox, running as a headless Spring Boot 4 application (no web layer).

## Stack

| Component | Version |
| --- | --- |
| Spring Boot | 4.2.0 |
| Kotlin | 2.4.10 |
| Java | 25 |
| Gradle | 9.9.2 |
| JUnit Jupiter | 6.1.3 (JUnit 6) |
| Kotest assertions | 6.2.7 |
| Kotlin DataFrame | 1.0.0-rc02 |

The DataFrame compiler plugin (`org.jetbrains.kotlin.plugin.dataframe`) ships with Kotlin
itself and replaces the former KSP based `org.jetbrains.kotlinx.dataframe` Gradle plugin.

## Running

```bash
./gradlew bootRun
```

The application reads `src/main/resources/sample.csv`, converts it to the typed
`CsvExampleDataModel` and logs the csv export.

## Testing

```bash
./gradlew build
```
