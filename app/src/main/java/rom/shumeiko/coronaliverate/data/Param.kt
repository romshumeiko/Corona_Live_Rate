package rom.shumeiko.coronaliverate.data

import rom.shumeiko.coronaliverate.R
import java.util.*

enum class Param(val id: Int, val paramTypeIconId: Int, val titleStatisticParam: String) {
    TOTAL_CASE(1, R.drawable.ic_total_cases, "Total Case"),
    TOTAL_DEATH(2, R.drawable.ic_death, "Total Death"),
    TOTAL_RECOVERED(3, R.drawable.ic_hearth, "Total Recovered"),
    NEW_CASES(4, R.drawable.ic_mask, "New cases"),
    NEW_DEATH(5, R.drawable.ic_new_death, "New deaths"),
    ACTIVE_CASES(6, R.drawable.ic_patient, "Active cases"),
    SERIOUS_CRITICAL(7, R.drawable.ic_alert, "Serious critical")
}