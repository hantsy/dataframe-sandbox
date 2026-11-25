package com.example

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CsvExampleServiceTest {
    private val csvExampleService = CsvExampleService()

    @Test
    fun `reads the sample csv into the typed model`() {
        val rows = csvExampleService.readSample()

        rows.size shouldBe 2
        rows[0].amount shouldBe BigDecimal("10.0")
        rows[0].last4 shouldBe "2345"
        rows[0].type shouldBe ACHType.NEXT_DAY
        rows[1].amount shouldBe BigDecimal("50.0")
        rows[1].last4 shouldBe "1234"
        rows[1].type shouldBe ACHType.SAME_DAY
    }

    @Test
    fun `exports the rows as csv`() {
        val csv = csvExampleService.export(csvExampleService.readSample())

        csv shouldStartWith "Amount,Last 4,ACH Type"
        csv shouldContain "10.0,2345,N"
        csv shouldContain "50.0,1234,S"
    }

    @Test
    fun `exports the rows in the column order of the data class constructor`() {
        val csv = csvExampleService.exportInConstructorOrder(csvExampleService.readSample())

        csv shouldStartWith "Amount,Last 4,ACH Type"
    }

    @Test
    fun `fails when the csv resource is missing`() {
        val failure =
            runCatching { csvExampleService.readSample("/missing.csv") }.exceptionOrNull()

        failure.shouldBeInstanceOf<IllegalArgumentException>()
    }
}
