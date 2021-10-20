package rom.shumeiko.coronaliverate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GlobalRate(val cases: Int, val deaths: Int, val recovered: Int) : Parcelable