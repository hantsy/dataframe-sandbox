package com.example

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.annotations.ColumnName
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import org.jetbrains.kotlinx.dataframe.api.ParserOptions
import org.jetbrains.kotlinx.dataframe.api.convertTo
import org.jetbrains.kotlinx.dataframe.api.parser
import org.jetbrains.kotlinx.dataframe.api.print
import org.jetbrains.kotlinx.dataframe.api.schema
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.api.toList
import org.jetbrains.kotlinx.dataframe.io.readCsv
import org.jetbrains.kotlinx.dataframe.io.toCsv
import org.jetbrains.kotlinx.dataframe.io.toCsvStr
import java.math.BigDecimal
import kotlin.reflect.typeOf

fun main() {
    println("Hello World!")
    val dataFrame = DataFrame
        .readCsv(
            inputStream = CsvExampleDataModel::class.java.getResourceAsStream("/sample.csv")!!,
            // see: https://github.com/Kotlin/dataframe/issues/998
            // and https://gist.github.com/Jolanrensen/863ae0144d9ed6309a0c11bab3d8ae9c
            parserOptions = ParserOptions(skipTypes = setOf(typeOf<Char>()))
        )
    dataFrame.schema().print()
    val result = dataFrame
        .convertTo<CsvExampleDataModel> {
            // freely convert
            parser { ACHType.fromSymbol(it) }
            // parser { BigDecimal(it) }
        }
        .toList()
    println("converted result:$result")

    val exportedDf = listOf(
        CsvExampleDataModel(BigDecimal("10.2"), "1234", ACHType.SAME_DAY),
        CsvExampleDataModel(BigDecimal("11.20"), "4567", ACHType.NEXT_DAY)
    ).toDataFrame()
    val exportedCsv = exportedDf.toCsv()
    println("old expected csv(Apache Commons Csv): \n$exportedCsv")
    val newExportedCsv = exportedDf.toCsvStr()
    println("new expected csv: \n$newExportedCsv")
}

enum class ACHType(val symbol: String) {
    SAME_DAY("S"), // same day
    NEXT_DAY("N"), // next day
    REAL_TIME("R"); // real time

    companion object {
        fun fromSymbol(symbol: String): ACHType = ACHType.entries.first { it.symbol == symbol }
    }
}

@DataSchema
data class CsvExampleDataModel(
    @ColumnName("Amount")
    val amount: BigDecimal? = null,
    @ColumnName("Last 4")
    val last4: String? = null,
    @ColumnName("ACH Type")
    val type: ACHType? = null
)