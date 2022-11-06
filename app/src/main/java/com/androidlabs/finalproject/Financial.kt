package com.androidlabs.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidlabs.finalproject.databinding.ActivityDonateBinding
import com.androidlabs.finalproject.databinding.ActivityFinancialBinding

class Financial : DrawerBaseActivity() {

    var activityFinancialBinding: ActivityFinancialBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityFinancialBinding = ActivityFinancialBinding.inflate(layoutInflater)
        allocateActivityTitle("Financial")
        setContentView(activityFinancialBinding!!.root)
    }
}