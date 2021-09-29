package rom.shumeiko.coronaliverate.ui.params.list

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.DialogFragment
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.databinding.FragmentParamsDialogBinding


class ParamsDialogFragment : DialogFragment() {

    private var _binding: FragmentParamsDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "ParamsDialogFragment"
        const val ARG_MESSAGE = "message"

        fun newInstance(message: String) : ParamsDialogFragment {
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }

            return ParamsDialogFragment().apply { arguments = args }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamsDialogBinding.inflate(inflater, container, false)
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
        dialog?.window?.attributes = params
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message = arguments?.getString(ARG_MESSAGE)
        binding.tvDescription.text = message

        binding.btnDialogOk.setOnClickListener {
            dialog?.dismiss()
        }
    }
}