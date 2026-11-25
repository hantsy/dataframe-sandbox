package com.example

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.annotations.ColumnName
import org.jetbrains.kotlinx.dataframe.api.ParserOptions
import org.jetbrains.kotlinx.dataframe.api.convertTo
import org.jetbrains.kotlinx.dataframe.api.parser
import org.jetbrains.kotlinx.dataframe.api.reorderColumnsBy
import org.jetbrains.kotlinx.dataframe.api.toDataFrame
import org.jetbrains.kotlinx.dataframe.api.toList
import org.jetbrains.kotlinx.dataframe.io.readCsv
import org.jetbrains.kotlinx.dataframe.io.toCsvStr
import org.springframework.stereotype.Service
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.typeOf

/**
 * Reads and writes the ACH sample data with Kotlin DataFrame.
 */
@Service
class CsvExampleService {
    /**
     * Reads the given csv resource and converts it into the typed data model.
     */
    fun readSample(resource: String = SAMPLE_CSV): List<CsvExampleDataModel> {
        val inputStream =
            CsvExampleService::class.java.getResourceAsStream(resource)
                ?: throw IllegalArgumentException("Resource $resource is not available")

        return inputStream.use { stream ->
            DataFrame
                .readCsv(
                    inputStream = stream,
                    // see: https://github.com/Kotlin/dataframe/issues/998
                    parserOptions = ParserOptions(skipTypes = setOf(typeOf<Char>())),
                ).convertTo<CsvExampleDataModel> {
                    // freely convert
                    parser { ACHType.fromSymbol(it) }
                }.toList()
        }
    }

    /**
     * Exports the given rows as a csv string.
     */
    fun export(rows: List<CsvExampleDataModel>): String = rows.toDataFrame().toCsvStr()

    /**
     * Exports the given rows as a csv string, keeping the column order of the data class constructor.
     */
    fun exportInConstructorOrder(rows: List<CsvExampleDataModel>): String {
        val constructorOrder = constructorColumnNames()

        return rows
            .toDataFrame()
            .reorderColumnsBy { constructorOrder.indexOf(it.name()) }
            .toCsvStr()
    }

    private fun constructorColumnNames(): List<String> =
        (CsvExampleDataModel::class as KClass<*>)
            .declaredMemberProperties
            .map { it.findAnnotation<ColumnName>()?.name ?: it.name }

    companion object {
        const val SAMPLE_CSV = "/sample.csv"
    }
}
