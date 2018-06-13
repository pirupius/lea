package com.agritech.lea

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import android.support.design.widget.BottomNavigationView
import com.agritech.lea.fragments.DashboardFragment
import com.agritech.lea.fragments.HomeFragment
import com.agritech.lea.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                val fragment = HomeFragment.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val fragment = DashboardFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val fragment = ProfileFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = HomeFragment.Companion.newInstance()
        addFragment(fragment)
    }

    /**
     * add/replace fragment in container [FrameLayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }
}
