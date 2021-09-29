package rom.shumeiko.coronaliverate.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.Param
import rom.shumeiko.coronaliverate.databinding.FragmentMainBinding
import rom.shumeiko.coronaliverate.storage.ParamsHolder
import rom.shumeiko.coronaliverate.storage.SharedPreferencesHelper
import rom.shumeiko.coronaliverate.storage.StatisticHolder
import rom.shumeiko.coronaliverate.ui.countries.list.CountriesFragment
import rom.shumeiko.coronaliverate.ui.countries.list.CountriesFragment.Companion.newInstance
import rom.shumeiko.coronaliverate.ui.countries.list.CountriesFragment.OnCountryClickedListener
import rom.shumeiko.coronaliverate.ui.params.list.ParamsDialogFragment
import rom.shumeiko.coronaliverate.ui.params.list.ParamsDialogFragment.Companion.TAG
import rom.shumeiko.coronaliverate.utils.UIUtils
import java.io.IOException


class MainFragment : Fragment() {

    var mainFragmentActionsListener: MainFragmentActionsListener? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {
        val globalRate = StatisticHolder.globalRate
        binding.globalRates.paramCoronavirusCases.ivIcon.setImageResource(R.drawable.ic_coronavirus_cases)
        binding.globalRates.paramCoronavirusCases.tvTitle.text = resources.getString(R.string.fragment_country_coronavirus_cases)



        binding.globalRates.paramDeath.ivIcon.setImageResource(R.drawable.ic_death)
        binding.globalRates.paramDeath.tvTitle.text = resources.getString(R.string.fragment_country_death)


        binding.globalRates.paramRecovered.ivIcon.setImageResource(R.drawable.ic_hearth)
        binding.globalRates.paramRecovered.tvTitle.text = resources.getString(R.string.fragment_country_recovered)





        
        binding.imbMenu.setOnClickListener {
            val menuDialogFragment = MenuDialogFragment()
            menuDialogFragment.onYourCountryClickedListener = onYourCountryClickedListener
            menuDialogFragment.show(childFragmentManager, MenuDialogFragment().tag)
        }

        binding.imbParams.setOnClickListener(onBtnParamsFragmentClickListener)
        binding.flShowAllCountries.setOnClickListener(onShowAllCountryClickListener)

        val countryList = StatisticHolder.countries ?: ArrayList()
        countryList.sortWith { o2, o1 -> o1.cases - o2.cases }

        val top10CountryList = ArrayList(countryList.subList(0, 9))

        openCountriesFragment(top10CountryList)
    }

    private fun observeData() {
        ParamsHolder.ldSelectedParams.observe(this, { selectedParams ->

            showFirstParam(selectedParams[0])
            showSecondParam(selectedParams.getOrNull(1))
            showThirdParam(selectedParams.getOrNull(2))
        })
    }

    private fun showFirstParam(id: Int) {
        Param.values().find { it.id == id }?.let {
            binding.ivIconFirstParam.setImageResource(it.paramTypeIconId)
            binding.tvTitleFirstParam.text = it.titleStatisticParam
        }
        binding.tvCountFirstParam.text = paramCountByCountry(id).toString()
        binding.tvStatisticOf24Hours.text = statisticOf24Hours(id)
        binding.rlFirstParam.isVisible = true
    }

    private fun showSecondParam(id: Int?) {
        when (id) {
            null -> {
                binding.llSecondAndThirdView.isVisible = false
            }
            else -> {
                Param.values().find { it.id == id }?.let {
                    binding.ivIconSecondParam.setImageResource(it.paramTypeIconId)
                    binding.tvTitleSecondParam.text = it.titleStatisticParam
                }
                binding.tvCountSecondParam.text = paramCountByCountry(id).toString()
                binding.llSecondAndThirdView.isVisible = true
            }
        }
    }

    private fun showThirdParam(id: Int?) {
        when (id) {
            null -> {
                binding.llThirdParam.isVisible = false
            }
            else -> {
                Param.values().find { it.id == id }?.let {
                    binding.ivIconThirdView.setImageResource(it.paramTypeIconId)
                    binding.tvTitleThirdParam.text = it.titleStatisticParam
                }
                binding.tvCountThirdParam.text = paramCountByCountry(id).toString()
                binding.llThirdParam.isVisible = true
            }
        }
    }

    private fun paramCountByCountry(id: Int): Int? {
        val country = StatisticHolder.countries?.find {
            it.countryInfo.id == SharedPreferencesHelper.getSelectedCountryId()
        }

        return when (id) {
            Param.TOTAL_CASE.id -> country?.cases
            Param.TOTAL_DEATH.id -> country?.deaths
            Param.TOTAL_RECOVERED.id -> country?.recovered
            Param.NEW_CASES.id -> country?.todayCases
            Param.NEW_DEATH.id -> country?.todayDeaths
            Param.ACTIVE_CASES.id -> country?.active
            Param.SERIOUS_CRITICAL.id -> country?.critical
            else -> throw IOException("Please add new Param")
        }
    }

    private fun paramCountByCountryTwoDaysAgo(id: Int): Int? {
        val country = StatisticHolder.countriesStatisticTwoDaysAgo?.find {
            it.countryInfo.id == SharedPreferencesHelper.getSelectedCountryId()
        }

        return when (id) {
            Param.TOTAL_CASE.id -> country?.cases
            Param.TOTAL_DEATH.id -> country?.deaths
            Param.TOTAL_RECOVERED.id -> country?.recovered
            Param.NEW_CASES.id -> country?.todayCases
            Param.NEW_DEATH.id -> country?.todayDeaths
            Param.ACTIVE_CASES.id -> country?.active
            Param.SERIOUS_CRITICAL.id -> country?.critical
            else -> throw IOException("Please add new Param")
        }
    }

    private fun statisticOf24Hours(id: Int) :  String {

        val statisticNow = paramCountByCountry(id)
        val statisticTwoDaysAgo = paramCountByCountryTwoDaysAgo(id)
        val statistic = statisticNow?.minus(statisticTwoDaysAgo!!)?.times(100)
            ?.div(statisticNow)

        return "$statistic%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openCountriesFragment(top10CountryList: ArrayList<Country>) {
        val countriesFragment = newInstance(top10CountryList, false, null)
        countriesFragment.onCountryClickedListener = onCountryClickedListener
        childFragmentManager
            .beginTransaction()
            .add(R.id.frNavigationFragment, countriesFragment, CountriesFragment::class.java.name)
            .addToBackStack(null)
            .commit()
    }

    private val onCountryClickedListener: OnCountryClickedListener =
        object : OnCountryClickedListener {
            override fun onCountryClicked(country: Country) {
                mainFragmentActionsListener?.onCountryClicked(country)
            }
        }

    private val onBtnParamsFragmentClickListener =
        View.OnClickListener { mainFragmentActionsListener?.onChangeParametersClicked() }

    private val onShowAllCountryClickListener =
        View.OnClickListener { mainFragmentActionsListener?.onShowAllCountry() }

    private val onYourCountryClickedListener : MenuDialogFragment.OnYourCountryClickedListener =
        object : MenuDialogFragment.OnYourCountryClickedListener {
            override fun onYourCountryClicked() {
                mainFragmentActionsListener?.onYourCountryClicked()
            }
        }

    interface MainFragmentActionsListener {
        fun onChangeParametersClicked()
        fun onCountryClicked(country: Country?)
        fun onShowAllCountry()
        fun onYourCountryClicked()
    }
}