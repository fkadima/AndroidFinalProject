package com.androidlabs.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import com.androidlabs.finalproject.databinding.ActivityMainBinding
import com.androidlabs.finalproject.databinding.ActivityResourcesBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: DrawerBaseActivity() {

    var activityMainActivityBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        activityMainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        allocateActivityTitle("Home Interface")
        setContentView(activityMainActivityBinding!!.root)

        val donateBtn = findViewById<Button>(R.id.donateBtn)
        donateBtn.setOnClickListener {
            val intent = Intent(this, Donate::class.java)
            startActivity(intent)
        }

        val requestsBtn = findViewById<Button>(R.id.requestsBtn)
        requestsBtn.setOnClickListener {
            val intent = Intent(this, Requests::class.java)
            startActivity(intent)
        }
    }
}