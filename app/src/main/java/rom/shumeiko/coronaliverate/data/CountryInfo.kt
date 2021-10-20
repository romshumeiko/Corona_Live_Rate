package rom.shumeiko.coronaliverate.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo(
    @Json(name = "_id") val id: Int,
    val iso2: String,
    val flag: String,
) : Parcelable