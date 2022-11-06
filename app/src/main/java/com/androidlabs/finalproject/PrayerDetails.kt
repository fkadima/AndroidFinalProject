package com.androidlabs.finalproject

data class PrayerDetails(
    val prayerId: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val prayerMessage: String? = null,
    val createdAt: String? = null
){
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}
