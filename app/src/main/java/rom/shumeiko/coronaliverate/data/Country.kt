package rom.shumeiko.coronaliverate.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Country(
    @SerializedName("country")
    var name: String,
    val countryInfo: CountryInfo,
    val cases: Int,
    val deaths: Int,
    val recovered: Int,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val active: Int,
    val critical: Int
) : Parcelable