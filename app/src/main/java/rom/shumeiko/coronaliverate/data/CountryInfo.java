package rom.shumeiko.coronaliverate.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CountryInfo implements Parcelable {

    @SerializedName("_id")
    public int id;
    public String iso2;
    public String flag;

    protected CountryInfo(Parcel in) {
        id = in.readInt();
        iso2 = in.readString();
        flag = in.readString();
    }

    public static final Creator<CountryInfo> CREATOR = new Creator<CountryInfo>() {
        @Override
        public CountryInfo createFromParcel(Parcel in) {
            return new CountryInfo(in);
        }

        @Override
        public CountryInfo[] newArray(int size) {
            return new CountryInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(iso2);
        dest.writeString(flag);
    }
}
