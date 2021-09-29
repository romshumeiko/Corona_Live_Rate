package rom.shumeiko.coronaliverate.ui.params.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Param
import rom.shumeiko.coronaliverate.ui.params.list.ParamsAdapter.ParamViewHolder

class ParamsAdapter() : RecyclerView.Adapter<ParamViewHolder>() {

    var onParamItemListener: OnParamItemListener? = null
    var paramList: ArrayList<Param>? = null
    var selectedParams: ArrayList<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_param, parent, false)
        return ParamViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParamViewHolder, position: Int) {
        val param: Param = paramList?.get(position) ?: return

        holder.ivIconParam.setImageResource(param.paramTypeIconId)
        holder.tvTitleParam.text = param.titleStatisticParam
        holder.itemView.setOnClickListener {
            onParamItemListener?.onParamClicked(param)
        }
        holder.ivCheckedParam.isSelected = isParamSelected(param)
    }

    private fun isParamSelected(param: Param): Boolean {
        return selectedParams?.find { it == param.id } != null
    }

    override fun getItemCount() = paramList?.size ?: 0

    class ParamViewHolder(itemView: View) : ViewHolder(itemView) {
        val ivIconParam: ImageView = itemView.findViewById(R.id.ivIconParam)
        val tvTitleParam: TextView = itemView.findViewById(R.id.tvTitleParam)
        val ivCheckedParam: ImageView = itemView.findViewById(R.id.ivSelectedParam)
    }

    interface OnParamItemListener {
        fun onParamClicked(param: Param)
    }
}