package rom.shumeiko.coronaliverate.storage

import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.GlobalRate

object StatisticHolder {

    var countries: ArrayList<Country>? = null
    var countriesStatisticTwoDaysAgo: ArrayList<Country>? = null
    var globalRate: GlobalRate? = null

    fun saveCountries(countries: ArrayList<Country>?) {
        this.countries = countries
    }

    fun saveCountriesStatisticTwoDaysAgo(countriesStatisticTwoDaysAgo: ArrayList<Country>?) {
        this.countriesStatisticTwoDaysAgo = countriesStatisticTwoDaysAgo
    }

    fun saveGlobalRate(globalRate: GlobalRate) {
        this.globalRate = globalRate
    }
}