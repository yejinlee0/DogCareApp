package com.example.dogcareapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView

class MedicationAdapter(): RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {
    lateinit var mDb: MedicationDB
    constructor(db: MedicationDB): this(){
        mDb = db
    }

    var medicationList: ArrayList<Medication> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_medication, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Medication = medicationList[position]
        holder.mCheckBox.text = item.medicine
        holder.mCheckBox.isChecked = toBoolean(item.status)
        holder.mCheckBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    mDb.updateStatus(item.id, 1)
                }
                else{
                    mDb.updateStatus(item.id, 0)
                }
            }
        })
    }

    private fun toBoolean(n: Int) = n != 0

    override fun getItemCount(): Int = medicationList.size

    fun setTask(medicationList: ArrayList<Medication>){
        this.medicationList = medicationList
        notifyDataSetChanged()
    }

    fun removeTask(position: Int){
        medicationList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val mCheckBox: CheckBox = view.findViewById(R.id.check_box)
    }
}