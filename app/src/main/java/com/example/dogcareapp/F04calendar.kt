package com.example.dogcareapp

import android.annotation.SuppressLint
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class F04calendar : Fragment(){
    lateinit var str: String
    lateinit var calendarView: CalendarView
    lateinit var button_save:Button
    lateinit var button_update: Button
    lateinit var button_delete:Button
    lateinit var text_schedule:TextView
    lateinit var editText_schedule: EditText
    lateinit var fileName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.f04calendar, container, false)

        calendarView=view.findViewById(R.id.calendarView)
        button_save=view.findViewById(R.id.button_save)
        button_update=view.findViewById(R.id.button_update)
        button_delete=view.findViewById(R.id.button_delete)
        text_schedule=view.findViewById(R.id.text_schedule)
        editText_schedule=view.findViewById(R.id.editText_schedule)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            text_schedule.visibility = View.INVISIBLE
            button_save.visibility = View.VISIBLE
            button_update.visibility = View.INVISIBLE
            button_delete.visibility = View.INVISIBLE
            editText_schedule.visibility = View.VISIBLE
            editText_schedule.setText("")
            check_schedule(year, month, dayOfMonth)
        }

        button_save.setOnClickListener {
            save_schedule(fileName)
            button_save.visibility = View.INVISIBLE
            button_update.visibility = View.VISIBLE
            button_delete.visibility = View.VISIBLE
            editText_schedule.visibility = View.INVISIBLE
            str = editText_schedule.text.toString()
            text_schedule.text = str
            text_schedule.visibility = View.VISIBLE
        }

        return view
    }

    fun check_schedule(cYear: Int, cMonth: Int, cDay: Int) {
        fileName = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt"

        try {
            val fileInputStream = requireContext().openFileInput(fileName)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
            button_save.visibility = View.INVISIBLE
            button_update.visibility = View.VISIBLE
            button_delete.visibility = View.VISIBLE
            editText_schedule.visibility = View.INVISIBLE
            text_schedule.visibility = View.VISIBLE
            text_schedule.text = str
            button_update.setOnClickListener {
                button_save.visibility = View.VISIBLE
                button_update.visibility = View.INVISIBLE
                button_delete.visibility = View.INVISIBLE
                editText_schedule.visibility = View.VISIBLE
                text_schedule.visibility = View.INVISIBLE
                editText_schedule.setText(str)
                text_schedule.text = editText_schedule.text
            }
            button_delete.setOnClickListener {
                button_save.visibility = View.VISIBLE
                button_update.visibility = View.INVISIBLE
                button_delete.visibility = View.INVISIBLE
                editText_schedule.setText("")
                editText_schedule.visibility = View.VISIBLE
                text_schedule.visibility = View.INVISIBLE
                remove_schedule(fileName)
            }
            if (text_schedule.text == null) {
                button_save.visibility = View.VISIBLE
                button_update.visibility = View.INVISIBLE
                button_delete.visibility = View.INVISIBLE
                editText_schedule.visibility = View.VISIBLE
                text_schedule.visibility = View.INVISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun remove_schedule(readDay: String?) {
        try {
            val fileOutputStream = requireContext().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun save_schedule(readDay: String?) {
        try {
            val fileOutputStream = requireContext().openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = editText_schedule.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}
