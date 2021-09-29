package rom.shumeiko.coronaliverate.ui.countries.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rom.shumeiko.coronaliverate.data.Country

class CountryViewModelFactory(private val application: Application,
                              private val country: Country) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountryViewModel(application, country) as T
    }
}