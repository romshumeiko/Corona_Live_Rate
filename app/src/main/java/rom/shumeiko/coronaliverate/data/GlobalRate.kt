package rom.shumeiko.coronaliverate.data

import android.os.Parcel
import android.os.Parcelable

data class GlobalRate(val cases: Int, val deaths: Int, val recovered: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cases)
        parcel.writeInt(deaths)
        parcel.writeInt(recovered)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GlobalRate> {
        override fun createFromParcel(parcel: Parcel): GlobalRate {
            return GlobalRate(parcel)
        }

        override fun newArray(size: Int): Array<GlobalRate?> {
            return arrayOfNulls(size)
        }
    }
}