package com.example.dogcareapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AllergyRecyclerviewAdapter (var allergies: ArrayList<Allergy>, var con: Context) : RecyclerView.Adapter<AllergyRecyclerviewAdapter.ViewHolder>(), Filterable {
    var filteredAllergies = ArrayList<Allergy>()
    var itemFilter = ItemFilter()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image_item_imgView: ImageView
        var name_item_txtView: TextView
        var rate_item_txtView: TextView

        init{
            image_item_imgView = itemView.findViewById(R.id.img_rating)
            name_item_txtView = itemView.findViewById(R.id.allergy_name)
            rate_item_txtView = itemView.findViewById(R.id.allergy_rating)

            itemView.setOnClickListener{
                AlertDialog.Builder(con).apply {
                    var position = bindingAdapterPosition
                    var allergy = filteredAllergies[position]
                    setTitle(allergy.name)
                    setMessage(allergy.rate)
                    when(allergy.imagePath){
                        "green_circle" -> setIcon(R.drawable.green_circle)
                        "grey_circle" -> setIcon(R.drawable.grey_circle)
                        "red_circle" -> setIcon(R.drawable.red_circle)
                        "yellow_circle" -> setIcon(R.drawable.yellow_circle)
                        else -> setIcon(R.drawable.allergy)
                    }
                    setPositiveButton("확인", null)
                    show()
                }
            }
        }
    }

    init {
        filteredAllergies.addAll(allergies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_allergy, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val allergy: Allergy = filteredAllergies[position]
        holder.name_item_txtView.text = allergy.name
        holder.rate_item_txtView.text = allergy.rate
        holder.image_item_imgView.setImageResource(
            when(allergy.imagePath){
                "green_circle" -> R.drawable.green_circle
                "grey_circle" -> R.drawable.grey_circle
                "red_circle" -> R.drawable.red_circle
                "yellow_circle" -> R.drawable.yellow_circle
                else -> R.drawable.allergy
            }
        )
    }

    override fun getItemCount(): Int {
        return filteredAllergies.size
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            val filteredList: ArrayList<Allergy> = ArrayList<Allergy>()
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = allergies
                results.count = allergies.size
                return results
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (person in allergies) {
                    if (person.name.contains(filterString)) {
                        filteredList.add(person)
                    }
                }
            } else {
                for (allergy in allergies) {
                    if (allergy.name.contains(filterString) || allergy.rate.contains(filterString)) {
                        filteredList.add(allergy)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredAllergies.clear()
            filteredAllergies.addAll(filterResults.values as ArrayList<Allergy>)
            notifyDataSetChanged()
        }
    }
}