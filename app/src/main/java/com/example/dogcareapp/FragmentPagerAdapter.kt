package com.example.dogcareapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    // 뷰페이저2에 연결할 프래크먼트를 리스트로 생로
    val fragmentList = listOf<Fragment>(F01_home(), F02_search(), F03_alarm(), F04_calendar())

    // 뷰페이저2에 노출시킬 프래그먼트 갯수 설정
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // 뷰페이저2의 각 페이지에서 노출할 프래그먼트 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}