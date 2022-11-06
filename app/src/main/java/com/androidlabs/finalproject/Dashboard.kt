package com.androidlabs.finalproject

import android.os.Bundle
import com.androidlabs.finalproject.databinding.ActivityDashboardBinding

class Dashboard : DrawerBaseActivity() {
    var activityDashboardBinding: ActivityDashboardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        allocateActivityTitle("Dashboard")
        setContentView(activityDashboardBinding!!.root)
    }
}