package com.androidlabs.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidlabs.finalproject.R
import com.androidlabs.finalproject.databinding.ActivityAboutBinding
import com.androidlabs.finalproject.databinding.ActivitySettingsBinding

class About : DrawerBaseActivity() {

    var activityAboutBinding: ActivityAboutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        allocateActivityTitle("About")
        setContentView(activityAboutBinding!!.root)
    }
}