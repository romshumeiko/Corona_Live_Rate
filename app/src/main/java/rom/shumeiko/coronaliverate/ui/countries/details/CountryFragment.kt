package rom.shumeiko.coronaliverate.ui.countries.details

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.StatisticTodayYesterday
import rom.shumeiko.coronaliverate.databinding.FragmentCountryBinding
import rom.shumeiko.coronaliverate.storage.StatisticHolder
import rom.shumeiko.coronaliverate.ui.main.MenuDialogFragment
import rom.shumeiko.coronaliverate.utils.UIUtils

class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var viewModel: CountryViewModel? = null

    companion object {
        private const val ARG_COUNTRY = "country"

        fun newInstance(country: Country): CountryFragment {
            val args = Bundle().apply {
                putParcelable(ARG_COUNTRY, country)
            }

            return CountryFragment().apply { arguments = args }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val country: Country = arguments?.getParcelable(ARG_COUNTRY) ?: return
        viewModel = ViewModelProviders.of(
            this,
            CountryViewModelFactory(
                requireActivity().application,
                country
            )
        ).get(CountryViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        with(binding.toolBar) {
            imbBack.setOnClickListener { activity?.onBackPressed() }
            imbMenu.setOnClickListener {
                MenuDialogFragment().show(
                    childFragmentManager,
                    MenuDialogFragment().tag
                )
            }
        }
    }

    private fun observeData() {
        viewModel?.ldCountry?.observe(this, { country ->

            viewModel?.ldStatisticTodayYesterday?.observe(this, { statisticTodayYesterday ->
                when (statisticTodayYesterday) {
                    StatisticTodayYesterday.TODAY -> showCountryStatistic(country)
                    StatisticTodayYesterday.YESTERDAY -> {
                        val countryTwoDaysAgo = StatisticHolder.countriesStatisticTwoDaysAgo?.find {
                            it.countryInfo.id == country.countryInfo.id
                        }
                        if (countryTwoDaysAgo != null) {
                            showCountryStatistic(countryTwoDaysAgo)
                        }
                    }
                }
            })

            binding.rgTodayYesterday.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rbToday -> {
                        showCountryStatistic(country)
                        viewModel?.onChangeStatisticDate(StatisticTodayYesterday.TODAY)
                    }
                    R.id.rbYesterday -> {
                        val countryTwoDaysAgo = StatisticHolder.countriesStatisticTwoDaysAgo?.find {
                            it.countryInfo.id == country.countryInfo.id
                        }
                        if (countryTwoDaysAgo != null) {
                            showCountryStatistic(countryTwoDaysAgo)
                            viewModel?.onChangeStatisticDate(StatisticTodayYesterday.YESTERDAY)
                        }
                    }
                }
            }

            binding.tvCountry.text = country.name
            val flagCornerRadius =
                requireActivity().resources.getDimensionPixelSize(R.dimen.country_fragment_country_flag_radius)

            Glide
                .with(this)
                .load(country.countryInfo.flag)
                .transform(CenterCrop(), RoundedCorners(flagCornerRadius))
                .into(binding.ivFlag)
        })
    }

    private fun showCountryStatistic(country: Country) {
        binding.paramCases.ivIcon.setImageResource(R.drawable.ic_total_cases)
        binding.paramCases.tvTitle.text =
            resources.getString(R.string.fragment_country_coronavirus_cases)
        binding.paramCases.tvValue.text = UIUtils.formatCases(country.todayCases)

        binding.paramDeaths.ivIcon.setImageResource(R.drawable.ic_death)
        binding.paramDeaths.tvTitle.text = resources.getString(R.string.fragment_country_death)
        binding.paramDeaths.tvValue.text = UIUtils.formatCases(country.todayDeaths)

        binding.paramRecovered.ivIcon.setImageResource(R.drawable.ic_hearth)
        binding.paramRecovered.tvTitle.text =
            resources.getString(R.string.fragment_country_recovered)
        binding.paramRecovered.tvValue.text = UIUtils.formatCases(country.todayRecovered)

        binding.tvCountActiveCases.text = UIUtils.formatCases(country.active)
        binding.tvCountClosedCases.text = ((country.deaths + country.recovered).toString())

        binding.paramRecoveredDischarged.ivIcon.setImageResource(R.drawable.ic_hearth)
        binding.paramRecoveredDischarged.tvCount.text =
            countRecoveredDeaths(country, Color.GREEN, country.todayRecovered)
        binding.paramRecoveredDischarged.tvTitle.text =
            resources.getString(R.string.fragment_country_recovered_discharged)

        binding.paramDeathsPercent.ivIcon.setImageResource(R.drawable.ic_death)
        binding.paramDeathsPercent.tvCount.text =
            countRecoveredDeaths(country, Color.RED, country.todayDeaths)
        binding.paramDeathsPercent.tvTitle.text =
            resources.getString(R.string.fragment_country_death)
    }

    private fun countRecoveredDeaths(
        country: Country,
        color: Int,
        statistic: Int
    ): SpannableStringBuilder {

        val builder = SpannableStringBuilder()
        val recoveredSpannable = SpannableStringBuilder(UIUtils.formatCases(country.todayRecovered))
        val percentRecovered =
            " (" + ((statistic * 100 / (country.todayRecovered + country.todayDeaths)).toString()) + "%)"
        val percentRecoveredSpannable = SpannableStringBuilder(percentRecovered)

        recoveredSpannable.setSpan(
            ForegroundColorSpan(color), 0,
            recoveredSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append(recoveredSpannable)

        percentRecoveredSpannable.setSpan(
            ForegroundColorSpan(Color.BLACK), 0,
            percentRecoveredSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        builder.append(percentRecoveredSpannable)

        return builder
    }
}