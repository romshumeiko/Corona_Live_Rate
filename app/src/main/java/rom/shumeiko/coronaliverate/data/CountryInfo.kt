package rom.shumeiko.coronaliverate.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo(
    @SerializedName("_id")
    val id: Int,
    val iso2: String,
    val flag: String,
) : Parcelable