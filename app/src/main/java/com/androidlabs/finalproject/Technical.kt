package com.androidlabs.finalproject

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidlabs.finalproject.databinding.ActivityTechnicalBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_prayer.*
import kotlinx.android.synthetic.main.activity_technical.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Technical : DrawerBaseActivity() {

    var activityTechnicalBinding: ActivityTechnicalBinding? = null

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseFirestore.setLoggingEnabled(true)

        activityTechnicalBinding = ActivityTechnicalBinding.inflate(layoutInflater)
        allocateActivityTitle("Technical")
        setContentView(activityTechnicalBinding!!.root)

        db = Firebase.firestore

        val fullname: EditText = findViewById<EditText>(R.id.fullNameTechnicalTf)
        val email: EditText = findViewById<EditText>(R.id.emailAddressTechnicalTf)
        val technicalDetails: EditText = findViewById<EditText>(R.id.technicalDetailsTf)
        val message: TextView = findViewById<TextView>(R.id.messageTextField)

        fullNameTechnicalTf.requestFocus()

        val clearTechnicalBtn = findViewById<Button>(R.id.clearTechnicalBtn)

        clearTechnicalBtn.setOnClickListener {
            fullname.text.clear()
            technicalDetails.text.clear()
            message.setText("")
            fullNameTechnicalTf.requestFocus()
            getAllTechRequests()
        }

        val submitTechnicalBtn = findViewById<Button>(R.id.submitTechnicalBtn)

        submitTechnicalBtn.setOnClickListener {
            submitTechnicalRequest()
        }
    }

    private fun randomUUID() = UUID.randomUUID().toString()

    private fun getAllTechRequests() {
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

    private fun submitTechnicalRequest() {
        val text = "Submitting your prayer request!"
        val duration = Toast.LENGTH_SHORT

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val current = LocalDateTime.now().format(formatter)

        val message: TextView = findViewById<TextView>(R.id.messageTextField)

        if (fullNameTechnicalTf.text.toString().trim().isEmpty()
            || technicalDetailsTf.text.toString().trim().isEmpty()
            || emailAddressTechnicalTf.text.toString().trim().isEmpty()
        ) {
            message.setText("All fields are required.")
        } else {

            val technicalRequest = TechnicalDetails(
                randomUUID(),
                fullNameTechnicalTf.text.toString(),
                emailAddressTechnicalTf.text.toString(),
                technicalDetailsTf.text.toString(),
                current
            )
            val uniqueId = randomUUID().toString()

            db.collection("technical_requests").document(uniqueId)
                .set(technicalRequest)
                .addOnSuccessListener { uniqueId ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: $uniqueId")
                    println("Successfully added")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    println("Something went wrong")
                }

            message.setTextColor(Color.parseColor("#008631"))
            message.setText("Validating... Please wait.")

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this, PrayerSubmissionConfirmation::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}