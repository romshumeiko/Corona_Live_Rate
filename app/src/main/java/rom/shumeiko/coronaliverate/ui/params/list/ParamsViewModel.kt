package rom.shumeiko.coronaliverate.ui.params.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Param
import rom.shumeiko.coronaliverate.storage.ParamsHolder
import rom.shumeiko.coronaliverate.storage.StatisticHolder
import kotlin.coroutines.coroutineContext

class ParamsViewModel(application: Application,
                      private val paramList: ArrayList<Param>) :
    AndroidViewModel(application) {

    companion object {
        const val MINIMUM_PARAMS_COUNT = 1
        const val MAXIMUM_PARAMS_COUNT = 3
    }

    val ldParamsList = MutableLiveData<ArrayList<Param>>()
    val ldSelectedParams = ParamsHolder.ldSelectedParams
    val ldShowDialog = MutableLiveData<String>()

    private val lessOneParams = application.getString(R.string.params_view_model_less_one_param)
    private val moreTreeParams = application.resources.getString(R.string.params_view_model_more_three_param)


    init {
        ldParamsList.value = paramList
    }

    fun onParamClicked(param: Param) {

        val selectedParams = ldSelectedParams.value
        val isParamSelected = selectedParams?.contains(param.id) ?: false

        if (isParamSelected) {
            if (selectedParams?.size == MINIMUM_PARAMS_COUNT) {
                showDialog(lessOneParams)
            } else {
                selectedParams?.remove(param.id)
                ParamsHolder.saveSelectedParams(selectedParams)
                ldSelectedParams.value = selectedParams
            }
        } else {
            if (selectedParams?.size == MAXIMUM_PARAMS_COUNT) {
                showDialog(moreTreeParams)
            } else {
                selectedParams?.add(param.id)
                ParamsHolder.saveSelectedParams(selectedParams)
                ldSelectedParams.value = selectedParams
            }
        }
    }

    private fun showDialog(message: String) {
        ldShowDialog.value = message
    }
}