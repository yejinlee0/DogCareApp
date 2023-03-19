package com.example.dogcareapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class F02_search : Fragment() {
    private lateinit var allergyRv: RecyclerView
    private lateinit var allergyRvAdapter: AllergyRvAdapter
    private lateinit var allergies: Array<Allergy>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allergies = tempAllergies()
        allergyRvAdapter = AllergyRvAdapter(allergies, requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f02_search, container, false)
        allergyRv = view.findViewById(R.id.allergy_rv)
        setAdapter()
        return view
    }

    private fun setAdapter() {
        allergyRv.layoutManager = LinearLayoutManager(requireContext())
        allergyRv.adapter = allergyRvAdapter
    }

    private fun tempAllergies(): Array<Allergy>{
        return arrayOf(
            Allergy(1, "바나나", "Level 3", "red_circle"),
            Allergy(2, "블루베리", "Level 3", "red_circle"),
            Allergy(3, "캔터루프 멜론", "Level 3", "red_circle"),
            Allergy(4, "크랜베리", "Insignificant Response", "grey_circle"),
            Allergy(5, "무화과", "Level 3", "red_circle"),
            Allergy(6, "허니듀 멜론", "Insignificant Response", "grey_circle"),
            Allergy(7, "키위", "Level 1", "green_circle"),
            Allergy(8, "망고", "Insignificant Response", "grey_circle"),
            Allergy(9, "오렌지", "Level 3", "red_circle"),
            Allergy(10, "복숭아", "Level 2", "yellow_circle"),
            Allergy(11, "배", "Level 3", "red_circle"),
            Allergy(12, "파인애플", "Level 2", "yellow_circle"),
            Allergy(13, "라즈베리", "Insignificant Response", "grey_circle"),
            Allergy(14, "사과", "Level 3", "red_circle")
        )
    }


}