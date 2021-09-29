package rom.shumeiko.coronaliverate.ui.main

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.DialogFragment
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.databinding.FragmentMenuDialogBinding

class MenuDialogFragment : DialogFragment() {

    private var _binding: FragmentMenuDialogBinding? = null
    private val binding get() = _binding!!

    var onYourCountryClickedListener: OnYourCountryClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val opacityValue = TypedValue()
        resources.getValue(R.dimen.fragment_params_dialog_opacity, opacityValue, true)
        val floatOpacityValue = opacityValue.float
        dialog?.window?.setDimAmount(floatOpacityValue)

        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.TOP
        dialog?.window?.attributes = params
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivCloseDialog.setOnClickListener {
            dialog?.dismiss()
        }

        initViews()
    }

    private fun initViews() {
        binding.yourCountry.ivIcon.setImageResource(R.drawable.ic_your_country)
        binding.yourCountry.tvTitle.text =
            resources.getString(R.string.fragment_menu_dialog_your_country)
        binding.getBitminder.ivIcon.setImageResource(R.drawable.ic_get_bitminder)
        binding.getBitminder.tvTitle.text =
            resources.getString(R.string.fragment_menu_dialog_get_bitminder)

        binding.yourCountry.root.setOnClickListener {
            onYourCountryClickedListener?.onYourCountryClicked()
            dialog?.dismiss()
        }
    }

    interface OnYourCountryClickedListener {
        fun onYourCountryClicked()
    }
}