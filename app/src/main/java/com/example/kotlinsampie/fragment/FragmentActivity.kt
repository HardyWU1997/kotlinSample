package com.example.kotlinsampie.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlinsampie.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentActivity : AppCompatActivity() {

    private lateinit var bottomNavigation :BottomNavigationView

    private val dashboardFragment = DashbosrdFragment()
    private val infoFragment = InfoFragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        replaceFragment(dashboardFragment)

        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_setting -> replaceFragment(settingFragment)
                R.id.ic_info -> replaceFragment(infoFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }

}