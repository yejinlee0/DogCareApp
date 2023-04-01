package com.example.dogcareapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class F03alarm : Fragment() {
    lateinit var medicationList: ArrayList<Medication>
    lateinit var adapter: MedicationAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var fab: ExtendedFloatingActionButton
    lateinit var medicationText: EditText
    lateinit var addButton: Button
    lateinit var db: MedicationDB
    lateinit var inputLayout: LinearLayout

    var gId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medicationList = ArrayList()
        db = MedicationDB(requireContext())
        adapter = MedicationAdapter(db)
        adapter.setTask(medicationList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.f03alarm, container, false)
        inputLayout = view.findViewById(R.id.input_section)
        medicationText = view.findViewById(R.id.medication_text)
        addButton = view.findViewById(R.id.add_button)
        fab = view.findViewById(R.id.fab)

        recyclerView = view.findViewById(R.id.medication_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        selectData()

        fab.setOnClickListener{
            viewMode("ADD")
        }

        addButton.setOnClickListener{
            viewMode("FAB")
            var text = medicationText.text.toString()
            if(addButton.text.toString() == "ADD"){
                val task = Medication(0, text, 0)
                db.addTask(task)
                selectReset("ADD")
            }
            else{
                db.updateTask(gId, text)
                selectReset("UPDATE")
            }
            hideKeyboard(medicationText, requireContext())
        }

        medicationText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    addButton.isEnabled = false
                    addButton.setTextColor(Color.GRAY)
                }
                else{
                    addButton.isEnabled = true
                    addButton.setTextColor(Color.BLACK)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        val id = medicationList[position].id
                        adapter.removeTask(position)
                        db.deleteTask(id)
                    }
                    ItemTouchHelper.RIGHT -> {
                        viewMode("UPDATE")
                        val task = medicationList[position].medicine
                        gId = medicationList[position].id
                        medicationText.setText(task)
                        addButton.text = "UPDATE"
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftLabel("삭제")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .addSwipeRightBackgroundColor(Color.BLUE)
                    .addSwipeRightLabel("수정")
                    .setSwipeRightLabelColor(Color.WHITE)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }).attachToRecyclerView(recyclerView)

        return view
    }

    private fun selectData(){
        medicationList = db.getAllTasks()
        medicationList.reverse()
        adapter.setTask(medicationList)
        adapter.notifyDataSetChanged()
    }

    private fun selectReset(type: String){
        selectData()
        medicationText.setText("")
        if(type != "ADD"){
            addButton.text = "ADD"
        }
    }

    private fun viewMode(type: String){
        if(type == "FAB"){
            inputLayout.visibility = View.GONE
            fab.visibility = View.VISIBLE
        }
        else{
            inputLayout.visibility = View.VISIBLE
            fab.visibility = View.INVISIBLE
        }
    }

    private fun hideKeyboard(editText: EditText, context: Context){
        val manager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(editText.applicationWindowToken, 0)
    }
}