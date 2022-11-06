package com.androidlabs.finalproject

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.androidlabs.finalproject.databinding.ActivityTechRequestsFirestoreBinding
import com.androidlabs.finalproject.databinding.ActivityTechnicalBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_technical.*
import java.util.*

class TechRequestsFirestore : DrawerBaseActivity() {

    var activityTechRequestsFirestore: ActivityTechRequestsFirestoreBinding? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var list: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseFirestore.setLoggingEnabled(true)

        activityTechRequestsFirestore = ActivityTechRequestsFirestoreBinding.inflate(layoutInflater)
        allocateActivityTitle("Tech Requests Firestore")
        setContentView(activityTechRequestsFirestore!!.root)

        db = Firebase.firestore

        getData()

    }

    fun getData() {
        db.collection("technical_requests")
            .get()
            .addOnCompleteListener {

                val result = StringBuffer()

                if (it.isSuccessful) {
                    for(document in it.result!!) {
                        result
                            .append("Request ID: " + document.data.getValue("techReqId"))
                            .append("\n")
                            .append("Full name: " + document.data.getValue("fullName"))
                            .append("\n")
                            .append("Email address: " + document.data.getValue("email"))
                            .append("\n")
                            .append("Message: " + document.data.getValue("techMessage"))
                            .append("\n\n")
                    }
                    textViewResult.setText(result)
                }
            }
    }
}