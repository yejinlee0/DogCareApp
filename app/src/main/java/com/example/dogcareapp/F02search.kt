package com.example.dogcareapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class F02search : Fragment() {
    private lateinit var allergyRecyclerview: RecyclerView
    private lateinit var allergyRecyclerviewAdapter: AllergyRecyclerviewAdapter
    private lateinit var allergies: ArrayList<Allergy>
    private lateinit var searchView_allergy: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allergies = tempAllergies()
        allergyRecyclerviewAdapter = AllergyRecyclerviewAdapter(allergies, requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.f02search, container, false)
        allergyRecyclerview = view.findViewById(R.id.recyclerView_allergy)
        searchView_allergy = view.findViewById(R.id.searchView_allergy)
        searchView_allergy.setOnQueryTextListener(searchViewTextListener)
        setAdapter()
        return view
    }

    private fun setAdapter() {
        allergyRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        allergyRecyclerview.adapter = allergyRecyclerviewAdapter
    }

    private fun tempAllergies(): ArrayList<Allergy>{
        var tempAllergies = ArrayList<Allergy>()
        tempAllergies.add(Allergy(1, "바나나", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(2, "블루베리", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(3, "캔터루프 멜론", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(4, "크랜베리", "Insignificant Response", "grey_circle"))
        tempAllergies.add(Allergy(5, "무화과", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(6, "허니듀 멜론", "Insignificant Response", "grey_circle"))
        tempAllergies.add(Allergy(7, "키위", "Level 1", "green_circle"))
        tempAllergies.add(Allergy(8, "망고", "Insignificant Response", "grey_circle"))
        tempAllergies.add(Allergy(9, "오렌지", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(10, "복숭아", "Level 2", "yellow_circle"))
        tempAllergies.add(Allergy(11, "배", "Level 3", "red_circle"))
        tempAllergies.add(Allergy(12, "파인애플", "Level 2", "yellow_circle"))
        tempAllergies.add(Allergy(13, "라즈베리", "Insignificant Response", "grey_circle"))
        tempAllergies.add(Allergy(14, "사과", "Level 3", "red_circle"))
        return tempAllergies
    }

    var searchViewTextListener: SearchView.OnQueryTextListener = object  : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            allergyRecyclerviewAdapter.filter.filter(newText)
            return false
        }
    }

}