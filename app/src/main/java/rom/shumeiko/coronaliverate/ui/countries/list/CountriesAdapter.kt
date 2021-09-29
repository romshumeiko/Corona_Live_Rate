package rom.shumeiko.coronaliverate.ui.countries.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rom.shumeiko.coronaliverate.R
import rom.shumeiko.coronaliverate.data.Country

class CountriesAdapter() : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    var onCountryItemListener: OnCountryItemListener? = null
    var countriesList: ArrayList<Country>? = null
    var selectedCountryId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country, parent, false)

        return CountriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country: Country = countriesList?.get(position) ?: return

        val flagCornerRadius = holder.itemView.resources.getDimensionPixelSize(R.dimen.country_fragment_country_flag_radius)

        Glide
                .with(holder.itemView)
                .load(country.countryInfo.flag)
                .placeholder(R.drawable.bg_default_country_flag)
                .transform(CenterCrop(), RoundedCorners(flagCornerRadius))
                .into(holder.ivCountryFlag)

        holder.tvTitleCountry.text = country.name
        holder.itemView.setOnClickListener {
            onCountryItemListener?.onCountryClicked(country)
        }

        holder.itemView.isSelected = selectedCountryId == country.countryInfo.id
    }

    override fun getItemCount() = countriesList?.size ?: 0

    class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCountryFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        val tvTitleCountry: TextView = itemView.findViewById(R.id.tvTitleCountry)
    }

    interface OnCountryItemListener {
        fun onCountryClicked(country: Country)
    }

    fun updateCountries(filteredCountries: ArrayList<Country>) {
        this.countriesList = filteredCountries
        notifyDataSetChanged()
    }
}