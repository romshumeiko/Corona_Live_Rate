package rom.shumeiko.coronaliverate.ui.params.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rom.shumeiko.coronaliverate.data.Param

class ParamsViewModelFactory(private val application: Application,
                             private val paramsList: ArrayList<Param>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ParamsViewModel(application, paramsList) as T
    }
}