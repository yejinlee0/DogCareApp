package com.example.dogcareapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AllergyRvAdapter (var allergies: Array<Allergy>, var con: Context) : RecyclerView.Adapter<AllergyRvAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        lateinit var image_item_imgView: ImageView
        lateinit var name_item_txtView: TextView
        lateinit var rate_item_txtView: TextView

        init{
            image_item_imgView = itemView.findViewById(R.id.rating_img)
            name_item_txtView = itemView.findViewById(R.id.allergy_name)
            rate_item_txtView = itemView.findViewById(R.id.allergy_rating)

            itemView.setOnClickListener{
                AlertDialog.Builder(con).apply {
                    var position = bindingAdapterPosition
                    var allergy = allergies[position]
                    setTitle(allergy.name)
                    setMessage(allergy.rate)
                    setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(con, "OK Button Click", Toast.LENGTH_SHORT).show()
                    })
                    show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val con = parent.context
        val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_allergy, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val allergy: Allergy = allergies[position]
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
        return allergies.size
    }
}