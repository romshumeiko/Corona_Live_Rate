package rom.shumeiko.coronaliverate.ui.params.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import rom.shumeiko.coronaliverate.data.Param
import rom.shumeiko.coronaliverate.databinding.FragmentParamsBinding
import rom.shumeiko.coronaliverate.ui.main.MenuDialogFragment
import java.io.Serializable
import java.util.*

class ParamsFragment : Fragment() {

    private var _binding: FragmentParamsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ParamsAdapter

    private var viewModel: ParamsViewModel? = null

    var onYourCountryClickedListeners : OnYourCountryClickedListeners? = null

    companion object {
        private const val ARG_PARAMS = "params"

        fun newInstance(params: ArrayList<Param>): ParamsFragment {
            val args = Bundle().apply {
                putSerializable(ARG_PARAMS, params as Serializable)
            }

            return ParamsFragment().apply { arguments = args }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val paramsList: ArrayList<Param> =
            arguments?.getSerializable(ARG_PARAMS) as ArrayList<Param>

        viewModel = ViewModelProviders.of(
            this,
            ParamsViewModelFactory(
                requireActivity().application,
                paramsList
            )
        ).get(ParamsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamsBinding.inflate(inflater, container, false)
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
        adapter = ParamsAdapter()
        adapter.onParamItemListener = onParamsItemListener

        with(binding.rvParams) {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@ParamsFragment.adapter
        }

        with(binding.toolBar) {
            imbBack.setOnClickListener { activity?.onBackPressed() }
            imbMenu.setOnClickListener {
                val menuDialogFragment = MenuDialogFragment()
                menuDialogFragment.onYourCountryClickedListener = onYourCountryClickedListener
                menuDialogFragment.show(
                    childFragmentManager,
                    MenuDialogFragment().tag
                )
            }
        }
    }

    private fun observeData() {
        viewModel?.ldParamsList?.observe(this, { paramList ->
            adapter.paramList = paramList
            adapter.notifyDataSetChanged()
        })

        viewModel?.ldSelectedParams?.observe(this, { selectedParams ->
            adapter.selectedParams = selectedParams
            adapter.notifyDataSetChanged()
        })

        viewModel?.ldShowDialog?.observe(this, { showDialog ->
            ParamsDialogFragment.newInstance(showDialog)
                .show(childFragmentManager, ParamsDialogFragment.TAG)
        })
    }

    private var onParamsItemListener: ParamsAdapter.OnParamItemListener =
        object : ParamsAdapter.OnParamItemListener {
            override fun onParamClicked(param: Param) {
                viewModel?.onParamClicked(param)
            }
        }

    private val onYourCountryClickedListener : MenuDialogFragment.OnYourCountryClickedListener =
        object : MenuDialogFragment.OnYourCountryClickedListener {
            override fun onYourCountryClicked() {
                onYourCountryClickedListeners?.onYourCountryClicked()
            }
        }

    interface OnYourCountryClickedListeners {
        fun onYourCountryClicked()
    }
}