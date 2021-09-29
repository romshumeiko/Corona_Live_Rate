package rom.shumeiko.coronaliverate.ui.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.GlobalRate


interface CoronaStatisticApi {

    @GET("v3/covid-19/countries")
    fun getStatistic(@Query("yesterday") yesterday: Boolean, @Query("twoDaysAgo") twoDaysAgo: Boolean) :
            Call<List<Country>>

    @GET("v3/covid-19/all")
    fun getGlobalStatistic(@Query("yesterday") yesterday: Boolean) : Call<GlobalRate>

    companion object {
        var BASE_URL = "https://corona.lmao.ninja"
    }
}