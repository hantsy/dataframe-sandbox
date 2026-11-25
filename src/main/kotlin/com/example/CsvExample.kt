package com.example

import org.jetbrains.kotlinx.dataframe.annotations.ColumnName
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema
import java.math.BigDecimal

enum class ACHType(
    val symbol: String,
) {
    SAME_DAY("S"), // same day
    NEXT_DAY("N"), // next day
    REAL_TIME("R"), // real time
    ;

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
    val type: ACHType? = null,
)
