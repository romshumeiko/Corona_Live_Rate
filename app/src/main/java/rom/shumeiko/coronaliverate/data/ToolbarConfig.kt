package rom.shumeiko.coronaliverate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToolbarConfig(
    val title: String, val showButtonBack: Boolean, val showButtonMenu: Boolean
) : Parcelable