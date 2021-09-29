package rom.shumeiko.coronaliverate.storage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rom.shumeiko.coronaliverate.data.Param

object ParamsHolder {

    val ldSelectedParams = MutableLiveData<ArrayList<Int>>()

    init {
        val selectedParams = getSelectedParams()
        if (selectedParams.isEmpty()) {
            selectedParams.add(Param.TOTAL_CASE.id)
            selectedParams.add(Param.ACTIVE_CASES.id)
            selectedParams.add(Param.NEW_CASES.id)

            saveSelectedParams(selectedParams)
        }
        ldSelectedParams.value = selectedParams
    }

    fun saveSelectedParams(selectedParams: ArrayList<Int>?) {
        SharedPreferencesHelper.saveFirstParamId(selectedParams?.getOrNull(0))
        SharedPreferencesHelper.saveSecondParamId(selectedParams?.getOrNull(1))
        SharedPreferencesHelper.saveThirdParamId(selectedParams?.getOrNull(2))
    }

    fun getSelectedParams() : ArrayList<Int> {
        val selectedParams = ArrayList<Int>()

        SharedPreferencesHelper.getFirstParamId()?.let {
            selectedParams.add(it)
        }

        SharedPreferencesHelper.getSecondParamId()?.let {
            selectedParams.add(it)
        }

        SharedPreferencesHelper.getThirdParamId()?.let {
            selectedParams.add(it)
        }

        return selectedParams
    }
}