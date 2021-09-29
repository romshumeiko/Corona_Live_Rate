package rom.shumeiko.coronaliverate.ui.countries.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import rom.shumeiko.coronaliverate.data.Country
import rom.shumeiko.coronaliverate.data.ToolbarConfig
import rom.shumeiko.coronaliverate.databinding.FragmentCountriesBinding
import rom.shumeiko.coronaliverate.ui.main.MenuDialogFragment
import java.util.*
import kotlin.collections.ArrayList


class CountriesFragment : Fragment() {

    private var _binding: FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    var onCountryClickedListener: OnCountryClickedListener? = null
    lateinit var adapter: CountriesAdapter

    private var viewModel: CountriesViewModel? = null

    companion object {

        private const val ARG_COUNTRIES = "countries"
        private const val ARG_SHOW_SEARCH = "search"
        private const val ARG_TOOLBAR_CONFIG = "toolbar"

        fun newInstance(
            countries: ArrayList<Country>, showSearch: Boolean = true,
            toolbarConfig: ToolbarConfig? = null
        ): CountriesFragment {
            val args = Bundle().apply {
                putParcelableArrayList(ARG_COUNTRIES, countries)
                putBoolean(ARG_SHOW_SEARCH, showSearch)
                putParcelable(ARG_TOOLBAR_CONFIG, toolbarConfig)
            }

            return CountriesFragment().apply { arguments = args }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countriesList: ArrayList<Country> =
            arguments?.getParcelableArrayList(ARG_COUNTRIES) ?: ArrayList()
        val showSearch = arguments?.getBoolean(ARG_SHOW_SEARCH) ?: true
        val toolbarConfig: ToolbarConfig? = arguments?.getParcelable(ARG_TOOLBAR_CONFIG)

        viewModel = ViewModelProviders.of(
            this,
            CountriesViewModelFactory(
                requireActivity().application,
                countriesList,
                showSearch,
                toolbarConfig
            )
        ).get(CountriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
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
        adapter = CountriesAdapter()
        adapter.onCountryItemListener = onCountryItemListener

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@CountriesFragment.adapter
        }

        with(binding.toolBar) {
            imbBack.setOnClickListener { activity?.onBackPressed() }
            imbMenu.setOnClickListener {
                MenuDialogFragment().show(
                    childFragmentManager,
                    MenuDialogFragment().tag
                )
            }
        }

        binding.edtSearch.doAfterTextChanged {
            viewModel?.onSearchChanged(it?.toString())
        }
    }

    private fun observeData() {
        viewModel?.ldCountries?.observe(this, { countries ->
            adapter.updateCountries(countries)
        })

        viewModel?.ldShowSearch?.observe(this, { showSearch ->
            binding.edtSearch.isVisible = showSearch
        })

        viewModel?.ldToolbarConfig?.observe(this, { toolbarConfig ->
            if (toolbarConfig == null) {
                binding.toolBar.root.isVisible = false

                return@observe
            }

            with(binding.toolBar) {
                tvToolBarTitle.text = toolbarConfig.title
                imbBack.isVisible = toolbarConfig.showButtonBack
                imbMenu.isVisible = toolbarConfig.showButtonMenu
            }
        })

        viewModel?.ldSelectedCountryId?.observe(this, { selectedCountryId ->
            adapter.selectedCountryId = selectedCountryId
            adapter.notifyDataSetChanged()
        })
    }

    private var onCountryItemListener: CountriesAdapter.OnCountryItemListener =
        object : CountriesAdapter.OnCountryItemListener {
            override fun onCountryClicked(country: Country) {
                onCountryClickedListener?.onCountryClicked(country)
            }
        }

    interface OnCountryClickedListener {
        fun onCountryClicked(country: Country)
    }
}