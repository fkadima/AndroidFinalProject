package com.androidlabs.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidlabs.finalproject.databinding.ActivityDashboardBinding
import com.androidlabs.finalproject.databinding.ActivitySettingsBinding

class Settings : DrawerBaseActivity() {

    var activitySettingsBinding: ActivitySettingsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        allocateActivityTitle("Settings")
        setContentView(activitySettingsBinding!!.root)
    }
}