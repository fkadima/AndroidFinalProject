package com.androidlabs.finalproject

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidlabs.finalproject.databinding.ActivityPrayerBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_prayer.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Prayer : DrawerBaseActivity() {

    var activityPrayerBinding: ActivityPrayerBinding? = null

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseFirestore.setLoggingEnabled(true)

        activityPrayerBinding = ActivityPrayerBinding.inflate(layoutInflater)
        allocateActivityTitle("Prayer")
        setContentView(activityPrayerBinding!!.root)

        db = Firebase.firestore

        val fullname: EditText = findViewById<EditText>(R.id.fullNameTf)
        val email: EditText = findViewById<EditText>(R.id.emailAddressTf)
        val prayerDetails: EditText = findViewById<EditText>(R.id.prayerDetailsTf)
        val message: TextView = findViewById<TextView>(R.id.messageTextField)

        fullNameTf.requestFocus()

        val clearPrayerBtn = findViewById<Button>(R.id.clearPrayerBtn)
        clearPrayerBtn.setOnClickListener {
            fullname.text.clear()
            email.text.clear()
            prayerDetails.text.clear()
            message.setText("")
            fullNameTf.requestFocus()
            getAllUsers()
        }

        val submitPrayerBtn = findViewById<Button>(R.id.submitPrayerBtn)
        submitPrayerBtn.setOnClickListener {
            submitPrayer()
        }
    }

    private fun randomUUID() = UUID.randomUUID().toString()

    private fun getAllUsers() {
        db.collection("prayer_requests")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun submitPrayer() {
        val text = "Submitting your prayer request!"
        val duration = Toast.LENGTH_SHORT

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val current = LocalDateTime.now().format(formatter)

        val message: TextView = findViewById<TextView>(R.id.messageTextField)

        if (fullNameTf.text.toString().trim().isEmpty()
            || prayerDetailsTf.text.toString().trim().isEmpty()
            || emailAddressTf.text.toString().trim().isEmpty()) {
            message.setText("All fields are required.")
        } else {

            val prayerRequest = PrayerDetails(
                randomUUID(),
                fullNameTf.text.toString(),
                emailAddressTf.text.toString(),
                prayerDetailsTf.text.toString(),
                current
            )
            val uniqueId = randomUUID().toString()

            db.collection("prayer_requests").document(uniqueId)
                .set(prayerRequest)
                .addOnSuccessListener { uniqueId ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $uniqueId")
                    println("Successfully added")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
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