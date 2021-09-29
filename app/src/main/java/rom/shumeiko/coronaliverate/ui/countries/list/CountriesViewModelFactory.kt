package rom.shumeiko.coronaliverate.ui.countries.list

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.ToolbarConfig
import java.util.ArrayList


class CountriesViewModelFactory(private val application: Application,
                                private val countries: ArrayList<Country>,
                                private val showSearch: Boolean = true,
                                private val toolbarConfig: ToolbarConfig? = null) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesViewModel(application, countries, showSearch, toolbarConfig) as T
    }
}