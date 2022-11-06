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
import com.androidlabs.finalproject.databinding.ActivityDonateBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_donate.*
import kotlinx.android.synthetic.main.activity_prayer.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.UUID.randomUUID

class Donate : DrawerBaseActivity() {

    var activityDonateBinding: ActivityDonateBinding? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDonateBinding = ActivityDonateBinding.inflate(layoutInflater)
        allocateActivityTitle("Donate")
        setContentView(activityDonateBinding!!.root)

        FirebaseFirestore.setLoggingEnabled(true)
        db = Firebase.firestore

        val firstname:EditText = findViewById<EditText>(R.id.firstnameTf)
        val lastname:EditText = findViewById<EditText>(R.id.lastnameTf)
        val address:EditText = findViewById<EditText>(R.id.addressTf)
        val city:EditText = findViewById<EditText>(R.id.cityTf)
        val state:EditText = findViewById<EditText>(R.id.stateTf)
        val amount:EditText = findViewById<EditText>(R.id.amountTf)
        val cardNumber:EditText = findViewById<EditText>(R.id.cardNumberTf)
        val email:EditText = findViewById<EditText>(R.id.emailTf)
        val phoneNumber:EditText = findViewById<EditText>(R.id.phoneNumberTf)
        val message:TextView = findViewById<TextView>(R.id.messageTv)

        firstnameTf.requestFocus()

        val clearBtn = findViewById<Button>(R.id.donationClearBtn)
        clearBtn.setOnClickListener {
            firstname.text.clear()
            lastname.text.clear()
            address.text.clear()
            city.text.clear()
            state.text.clear()
            amount.text.clear()
            cardNumber.text.clear()
            email.text.clear()
            phoneNumber.text.clear()
            firstnameTf.requestFocus()
            message.setText("")
        }

        val submitBtn = findViewById<Button>(R.id.donationSubmitBtn)
        submitBtn.setOnClickListener {
            submitDonation()
        }
    }

    private fun getAllDonations() {
        db.collection("donations")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun randomUUID() = UUID.randomUUID().toString()

    private fun submitDonation() {

        val text = "Submitting your donation!"
        val duration = Toast.LENGTH_SHORT

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val current = LocalDateTime.now().format(formatter)

        val message:TextView = findViewById<TextView>(R.id.messageTv)

        if (firstnameTf.text.toString().trim().isEmpty() || lastnameTf.text.toString().trim().isEmpty() || amountTf.text.toString().trim().isEmpty() || cardNumberTf.text.toString().trim().isEmpty()) {

            message.setText("Please enter all required fields.")
        } else {

            val donationRequest = DonationDetails(
                randomUUID(),
                current,
                firstnameTf.text.toString(),
                lastnameTf.text.toString(),
                addressTf.text.toString(),
                cityTf.text.toString(),
                stateTf.text.toString(),
                amountTf.text.toString(),
                cardNumberTf.text.toString(),
                emailTf.text.toString(),
                phoneNumberTf.text.toString()
            )

            val uniqueId = randomUUID().toString()

            db.collection("donations").document(uniqueId)
                .set(donationRequest)
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
                val intent = Intent(this, Thankyou::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}