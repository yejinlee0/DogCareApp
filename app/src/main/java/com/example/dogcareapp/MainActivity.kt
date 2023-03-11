package com.example.dogcareapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.dogcareapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewPager2, TabLayout 참조
        val viewPager2:ViewPager2 = findViewById(R.id.viewpager2)
        val tableLayout:TabLayout = findViewById(R.id.tablayout)

        // FragmentPagerAdapter 생성
        val fragmentPagerAdapter = FragmentPagerAdapter(this)

        // 뷰페이저2의 어댑터 설정
        viewPager2.adapter = fragmentPagerAdapter

        // 탭 항목의 아이콘과 이름을 배열정 생성
        val tabIcons = arrayOf(R.drawable.home, R.drawable.allergy, R.drawable.medicine, R.drawable.calendar)
        val tabNames = arrayOf("홈", "알러지", "복약", "캘린더")

        // 뷰페이저와 탭레이아웃을 연결, 탭 항목의 아이콘과 이름 설정
        TabLayoutMediator(tableLayout, viewPager2) {tab, position ->
            tab.setIcon(tabIcons[position])
            tab.text = tabNames[position]
        }.attach()

    }
}