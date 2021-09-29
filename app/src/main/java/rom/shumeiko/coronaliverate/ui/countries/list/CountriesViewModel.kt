package rom.shumeiko.coronaliverate.ui.countries.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.ToolbarConfig
import rom.shumeiko.coronaliverate.storage.SharedPreferencesHelper
import java.util.*


class CountriesViewModel(application: Application,
                         private val countries: ArrayList<Country>,
                         private val showSearch: Boolean = true,
                         private val toolbarConfig: ToolbarConfig? = null) : AndroidViewModel(application) {

    val ldCountries = MutableLiveData<ArrayList<Country>>()
    val ldShowSearch = MutableLiveData<Boolean>()
    val ldToolbarConfig = MutableLiveData<ToolbarConfig?>()
    val ldSelectedCountryId = MutableLiveData<Int>()

    init {
        ldCountries.value = countries
        ldShowSearch.value = showSearch
        ldToolbarConfig.value = toolbarConfig
        ldSelectedCountryId.value = SharedPreferencesHelper.getSelectedCountryId()
    }

    fun onSearchChanged(search: String?) {
        val filteredCountries: ArrayList<Country> = ArrayList()
        for (country in countries) {
            if (country.name?.contains(search ?: "", true) == true) {
                filteredCountries.add(country)
            }
        }
        ldCountries.value = filteredCountries
    }
}