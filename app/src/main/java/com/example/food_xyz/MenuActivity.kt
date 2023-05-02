package com.example.food_xyz

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.food_xyz.fragment.MainMenuActivity
import com.example.food_xyz.fragment.ProfileMenuactivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        var idUser = intent?.getStringExtra("id_user")?.toString()

        val bundle = Bundle()

        bundle.putString("id_user", idUser)

        val mainMenuActivity = MainMenuActivity()
        val profileMenuactivity = ProfileMenuactivity()

        mainMenuActivity.arguments = bundle
        profileMenuactivity.arguments = bundle

        setCurrentFragment(mainMenuActivity)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.mi_cart -> setCurrentFragment(mainMenuActivity)
                R.id.mi_person -> setCurrentFragment(profileMenuactivity)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment,fragment)
            commit()
        }
    }


}