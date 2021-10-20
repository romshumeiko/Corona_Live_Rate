package rom.shumeiko.coronaliverate.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.GlobalRate
import rom.shumeiko.coronaliverate.storage.StatisticHolder

class SplashViewModel : ViewModel() {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val ldDataLoaded = MutableLiveData<Boolean>()
    private val retrofit = Retrofit.Builder()
        .baseUrl(CoronaStatisticApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val coronaStatisticApi = retrofit.create(CoronaStatisticApi::class.java)

    init {
        val call = coronaStatisticApi.getStatistic(true, false)

        call.enqueue(object : Callback<List<Country>> {
            override fun onResponse(
                call: Call<List<Country>>?,
                response: Response<List<Country>>?
            ) {
                val countries: List<Country>? = response?.body()
                val countryList: ArrayList<Country> = ArrayList()
                if (countries != null) {
                    countryList.addAll(countries)
                }
                StatisticHolder.saveCountries(countryList)
                if (response?.isSuccessful == true) {
                    loadStatisticTwoDaysAgo()
                }
            }

            override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    private fun loadStatisticTwoDaysAgo() {
        val callStatisticTwoDaysAgo = coronaStatisticApi.getStatistic(false, true)
        callStatisticTwoDaysAgo.enqueue(object : Callback<List<Country>> {
            override fun onResponse(
                callStatisticTwoDaysAgo: Call<List<Country>>?,
                response: Response<List<Country>>?
            ) {
                val countries: List<Country>? = response?.body()
                val countryList: ArrayList<Country> = ArrayList()
                if (countries != null) {
                    countryList.addAll(countries)
                }
                StatisticHolder.saveCountriesStatisticTwoDaysAgo(countryList)
                if (response?.isSuccessful == true) {
                    loadStatisticGlobalRate()
                }
            }

            override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }

    private fun loadStatisticGlobalRate() {
        val callStatisticGlobalRate = coronaStatisticApi.getGlobalStatistic(true)
        callStatisticGlobalRate.enqueue(object : Callback<GlobalRate> {
            override fun onResponse(
                callStatisticGlobalRate: Call<GlobalRate>,
                response: Response<GlobalRate>?
            ) {
                val globalRate: GlobalRate? = response?.body()
                if (globalRate != null) {
                    StatisticHolder.saveGlobalRate(globalRate)
                }
                ldDataLoaded.value = true
            }

            override fun onFailure(call: Call<GlobalRate>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}