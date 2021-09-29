package rom.shumeiko.coronaliverate.ui.countries.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.StatisticTodayYesterday

class CountryViewModel(application: Application, private val country: Country) : AndroidViewModel(application) {

    val ldCountry = MutableLiveData<Country>()
    val ldStatisticTodayYesterday = MutableLiveData<StatisticTodayYesterday>()

    init {
        ldCountry.value = country
        ldStatisticTodayYesterday.value = StatisticTodayYesterday.TODAY
    }

    fun onChangeStatisticDate(value: StatisticTodayYesterday) {
        ldStatisticTodayYesterday.value = value
    }
}