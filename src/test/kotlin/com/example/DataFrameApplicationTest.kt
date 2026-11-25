package com.example

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DataFrameApplicationTest {
    @Autowired
    lateinit var csvExampleService: CsvExampleService

    @Test
    fun `the csv example service is wired into the application context`() {
        csvExampleService shouldNotBe null
    }

    @Test
    fun `the sample csv is readable through the service`() {
        csvExampleService.readSample().size shouldBe 2
    }
}
