package com.androidlabs.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidlabs.finalproject.databinding.ActivityPrayerRequestsFirestoreBinding
import com.androidlabs.finalproject.databinding.ActivityTechnicalBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_prayer_requests_firestore.*
import kotlinx.android.synthetic.main.activity_technical.*

class PrayerRequestsFirestore : DrawerBaseActivity() {

    var activityPrayerRequestsFirestoreBinding: ActivityPrayerRequestsFirestoreBinding? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseFirestore.setLoggingEnabled(true)

        activityPrayerRequestsFirestoreBinding = ActivityPrayerRequestsFirestoreBinding.inflate(layoutInflater)
        allocateActivityTitle("Prayer Requests Firestore")
        setContentView(activityPrayerRequestsFirestoreBinding!!.root)

        db = Firebase.firestore

        getPrayerRequestsData()

    }

    fun getPrayerRequestsData() {
        db.collection("prayer_requests")
            .get()
            .addOnCompleteListener {

                val result = StringBuffer()

                if (it.isSuccessful) {
                    for(document in it.result!!) {
                        result
                            .append("ID: " + document.data.getValue("prayerId"))
                            .append("\n").append("Date: " + document.data.getValue("createdAt"))
                            .append("\n")
                            .append("Full name: " + document.data.getValue("fullName"))
                            .append("\n")
                            .append("Email address: " + document.data.getValue("email"))
                            .append("\n")
                            .append("Message: " + document.data.getValue("prayerMessage"))
                            .append("\n\n\n")
                    }
                    textViewResultTf.setText(result)
                }
            }
    }
}