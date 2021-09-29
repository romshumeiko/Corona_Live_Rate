package rom.shumeiko.coronaliverate.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object UIUtils {
    fun formatCases(cases: Int): String {
        val symbols = DecimalFormatSymbols()
        symbols.groupingSeparator = ','
        val decimalFormat = DecimalFormat("###,###,##0", symbols)

        return decimalFormat.format(cases.toLong())
    }
}