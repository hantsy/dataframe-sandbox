package com.example

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Runs the Kotlin DataFrame sample when the headless application starts.
 */
@Component
class CsvExampleRunner(
    private val csvExampleService: CsvExampleService,
) : CommandLineRunner {
    companion object {
        private val log = LoggerFactory.getLogger(CsvExampleRunner::class.java)
    }

    override fun run(vararg args: String) {
        val rows = csvExampleService.readSample()

        log.info("read {} rows from the sample csv", rows.size)
        rows.forEach { log.info("row: {}", it) }

        log.info("exporting csv:\n{}", csvExampleService.export(rows))
        log.info(
            "exporting csv using column name order in constructor:\n{}",
            csvExampleService.exportInConstructorOrder(rows),
        )
    }
}
