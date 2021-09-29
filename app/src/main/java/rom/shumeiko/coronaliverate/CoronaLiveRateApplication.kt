package rom.shumeiko.coronaliverate

import android.app.Application
import rom.shumeiko.coronaliverate.storage.SharedPreferencesHelper

class CoronaLiveRateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPreferencesHelper.init(this)
    }
}