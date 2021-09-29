package rom.shumeiko.coronaliverate.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.Param
import rom.shumeiko.coronaliverate.data.ToolbarConfig
import rom.shumeiko.coronaliverate.databinding.ActivityNavigationBinding
import rom.shumeiko.coronaliverate.storage.SharedPreferencesHelper
import rom.shumeiko.coronaliverate.storage.StatisticHolder
import rom.shumeiko.coronaliverate.ui.countries.details.CountryFragment
import rom.shumeiko.coronaliverate.ui.countries.list.CountriesFragment
import rom.shumeiko.coronaliverate.ui.params.list.ParamsFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val countAppLogin = SharedPreferencesHelper.getCountAppLogin()
        if (countAppLogin == null) {
            SharedPreferencesHelper.saveCountAppLogin(1)
        } else {
            SharedPreferencesHelper.saveCountAppLogin(countAppLogin + 1)
        }

        val splashFragmentListener: SplashFragment.SplashFragmentListener =
            object : SplashFragment.SplashFragmentListener {
                override fun onDataLoaded() {
                    if (countAppLogin == null) {
                        val countriesFragment = CountriesFragment.newInstance(
                            StatisticHolder.countries ?: ArrayList(),
                            true, ToolbarConfig(
                                resources.getString(R.string.navigation_activity_select_your_country),
                                false, false
                            )
                        )
                        countriesFragment.onCountryClickedListener = onCountryClickedListener
                        openFragment(countriesFragment, CountriesFragment::class.java.name)
                    } else {
                        val mainFragment = MainFragment()
                        mainFragment.mainFragmentActionsListener = mainFragmentActionsListener
                        openFragment(mainFragment, MainFragment::class.java.name)
                    }
                }
            }

        val splashFragment = SplashFragment()
        splashFragment.splashFragmentListener = splashFragmentListener
        openFragment(splashFragment, SplashFragment::class.java.name)
    }

    private fun getParamsList(): ArrayList<Param> {
        val params = ArrayList<Param>()
        params.addAll(Param.values())

        return params
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frNavigationFragment, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    private val mainFragmentActionsListener: MainFragment.MainFragmentActionsListener =
        object : MainFragment.MainFragmentActionsListener {
            override fun onChangeParametersClicked() {
                val paramsFragment = ParamsFragment.newInstance(getParamsList())
                paramsFragment.onYourCountryClickedListeners = onYourCountryClickedListeners
                openFragment(paramsFragment, ParamsFragment::class.java.name)
            }

            override fun onCountryClicked(country: Country?) {
                if (country == null) return

                val countryFragment = CountryFragment.newInstance(country)
                openFragment(countryFragment, CountryFragment::class.java.name)
            }

            override fun onShowAllCountry() {
                val countriesFragment = CountriesFragment.newInstance(
                    StatisticHolder.countries ?: ArrayList(),
                    true, ToolbarConfig(
                        resources.getString(
                            R.string.navigation_activity_all_countries
                        ),
                        true, true
                    )
                )
                countriesFragment.onCountryClickedListener = onCountryClickedListeners
                openFragment(countriesFragment, CountriesFragment::class.java.name)
            }

            override fun onYourCountryClicked() {
                val countriesFragment = CountriesFragment.newInstance(
                    StatisticHolder.countries ?: ArrayList(),
                    true, ToolbarConfig(
                        resources.getString(R.string.navigation_activity_select_your_country),
                        false, false
                    )
                )
                countriesFragment.onCountryClickedListener = onCountryClickedListener
                openFragment(countriesFragment, CountriesFragment::class.java.name)
            }
        }

    private val onCountryClickedListener: CountriesFragment.OnCountryClickedListener =
        object : CountriesFragment.OnCountryClickedListener {
            override fun onCountryClicked(country: Country) {
                SharedPreferencesHelper.saveSelectedCountryId(country.countryInfo.id)
                val mainFragment = MainFragment()
                mainFragment.mainFragmentActionsListener = mainFragmentActionsListener
                openFragment(mainFragment, MainFragment::class.java.name)
            }
        }

    private val onCountryClickedListeners: CountriesFragment.OnCountryClickedListener =
        object : CountriesFragment.OnCountryClickedListener {
            override fun onCountryClicked(country: Country) {
                mainFragmentActionsListener.onCountryClicked(country)
            }
        }

    private val onYourCountryClickedListeners: ParamsFragment.OnYourCountryClickedListeners =
        object : ParamsFragment.OnYourCountryClickedListeners {
            override fun onYourCountryClicked() {
                val countriesFragment = CountriesFragment.newInstance(
                    StatisticHolder.countries ?: ArrayList(),
                    true, ToolbarConfig(
                        resources.getString(R.string.navigation_activity_select_your_country),
                        false, false
                    )
                )
                countriesFragment.onCountryClickedListener = onCountryClickedListener
                openFragment(countriesFragment, CountriesFragment::class.java.name)
            }
        }
}
