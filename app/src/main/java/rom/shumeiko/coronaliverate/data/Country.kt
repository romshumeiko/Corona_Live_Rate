package rom.shumeiko.coronaliverate.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable {

    @SerializedName("country")
     public String name;
     public CountryInfo countryInfo;
     public int cases;
     public int deaths;
     public int recovered;
     public int todayCases;
     public int todayDeaths;
     public int todayRecovered;
     public int active;
     public int critical;

    protected Country(Parcel in) {
        name = in.readString();
        countryInfo = in.readParcelable(CountryInfo.class.getClassLoader());
        cases = in.readInt();
        deaths = in.readInt();
        recovered = in.readInt();
        todayCases = in.readInt();
        todayDeaths = in.readInt();
        todayRecovered = in.readInt();
        active = in.readInt();
        critical = in.readInt();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(countryInfo, flags);
        dest.writeInt(cases);
        dest.writeInt(deaths);
        dest.writeInt(recovered);
        dest.writeInt(todayCases);
        dest.writeInt(todayDeaths);
        dest.writeInt(todayRecovered);
        dest.writeInt(active);
        dest.writeInt(critical);
    }
}

