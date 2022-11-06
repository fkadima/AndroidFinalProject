package com.androidlabs.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.androidlabs.finalproject.databinding.ActivityDonateBinding
import com.androidlabs.finalproject.databinding.ActivityRequestsBinding

class Requests : DrawerBaseActivity() {

    var activityRequestsBinding: ActivityRequestsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRequestsBinding = ActivityRequestsBinding.inflate(layoutInflater)
        allocateActivityTitle("Request")
        setContentView(activityRequestsBinding!!.root)


        val prayerBtn = findViewById<Button>(R.id.prayerBtn)
        prayerBtn.setOnClickListener {
            val intent = Intent(this, Prayer::class.java)
            startActivity(intent)
        }

        val financialBtn = findViewById<Button>(R.id.financialBtn)
        financialBtn.setOnClickListener {
            val intent = Intent(this, Financial::class.java)
            startActivity(intent)
        }

        val technicalBtn = findViewById<Button>(R.id.technicalBtn)
        technicalBtn.setOnClickListener {
            val intent = Intent(this, Technical::class.java)
            startActivity(intent)
        }

        val myTechnicalRequestsBtn = findViewById<Button>(R.id.myTechnicalRequestsBtn)
        myTechnicalRequestsBtn.setOnClickListener {
            val intent = Intent(this, TechRequestsFirestore::class.java)
            startActivity(intent)
        }

        val prayerRequestsBtn = findViewById<Button>(R.id.prayerRequestsBtn)
        prayerRequestsBtn.setOnClickListener {
            val intent = Intent(this, PrayerRequestsFirestore::class.java)
            startActivity(intent)
        }
    }
}