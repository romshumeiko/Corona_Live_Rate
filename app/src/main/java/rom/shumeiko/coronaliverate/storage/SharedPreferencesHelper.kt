package rom.shumeiko.coronaliverate.storage

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    private const val PREFS_CORONA_LIVE = "Settings App Corona Live"
    private const val SELECTED_COUNTRY_ID = "Selected country id"
    const val COUNT_APP_LOGIN = "Application login counter"
    const val FIRST_PARAM_ID = "First Param ID"
    const val SECOND_PARAM_ID = "Second Param ID"
    const val THIRD_PARAM_ID = "Third Param ID"
    const val NO_VALUE = -1

    private lateinit var preferences: SharedPreferences


    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_CORONA_LIVE, Context.MODE_PRIVATE)
    }

    fun getSelectedCountryId() : Int? {
        return getIntValue(SELECTED_COUNTRY_ID)
    }

    fun saveSelectedCountryId(selectedCountryId: Int?) {
        saveIntValue(selectedCountryId, SELECTED_COUNTRY_ID)
    }

    fun getCountAppLogin() : Int? {
        return getIntValue(COUNT_APP_LOGIN)
    }

    fun saveCountAppLogin(countAppLogin: Int?) {
        saveIntValue(countAppLogin, COUNT_APP_LOGIN)
    }


    fun saveFirstParamId(id: Int?) {
        saveIntValue(id, FIRST_PARAM_ID)
    }

    fun getFirstParamId() : Int? {
        return getIntValue(FIRST_PARAM_ID)
    }

    fun saveSecondParamId(id: Int?) {
        saveIntValue(id, SECOND_PARAM_ID)
    }

    fun getSecondParamId() : Int? {
        return getIntValue(SECOND_PARAM_ID)
    }

    fun saveThirdParamId(id: Int?) {
        saveIntValue(id, THIRD_PARAM_ID)
    }

    fun getThirdParamId() : Int? {
        return getIntValue(THIRD_PARAM_ID)
    }

    private fun saveIntValue(value: Int?, key: String) {
        if (value == null) {
            preferences.edit().remove(key).apply()
        } else {
            preferences.edit().putInt(key, value).apply()
        }
    }

    private fun getIntValue(key: String): Int? {
        val value = preferences.getInt(key, NO_VALUE)
        return if (value == NO_VALUE) null else value
    }
}