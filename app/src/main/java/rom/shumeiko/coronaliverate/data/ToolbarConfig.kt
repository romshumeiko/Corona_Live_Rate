package rom.shumeiko.coronaliverate.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ToolbarConfig implements Parcelable {

    public String title;
    public Boolean showButtonBack;
    public Boolean showButtonMenu;

    public ToolbarConfig(String title, Boolean showButtonBack, Boolean showButtonMenu) {
        this.title = title;
        this.showButtonBack = showButtonBack;
        this.showButtonMenu = showButtonMenu;
    }

    protected ToolbarConfig(Parcel in) {
        title = in.readString();
        byte tmpShowButtonBack = in.readByte();
        showButtonBack = tmpShowButtonBack == 0 ? null : tmpShowButtonBack == 1;
        byte tmpShowButtonMenu = in.readByte();
        showButtonMenu = tmpShowButtonMenu == 0 ? null : tmpShowButtonMenu == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeByte((byte) (showButtonBack == null ? 0 : showButtonBack ? 1 : 2));
        dest.writeByte((byte) (showButtonMenu == null ? 0 : showButtonMenu ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToolbarConfig> CREATOR = new Creator<ToolbarConfig>() {
        @Override
        public ToolbarConfig createFromParcel(Parcel in) {
            return new ToolbarConfig(in);
        }

        @Override
        public ToolbarConfig[] newArray(int size) {
            return new ToolbarConfig[size];
        }
    };
}
