package com.example.cloudkicks
/*
| Thabang Agnes Ramatlakana ST10088708 |
| Makgalotso Maketela ST10089280
| Itumeleng Tshabane ST10088688 |
| Juan Romeo Springbok ST10089499 |
| Daniel Fernandes Lourenço ST10088840 |

“Save Data in Firebase Realtime Database in Android Studio Using Kotlin.” Www.youtube.com, www.youtube.com/watch?v=pLnhnHwLkYo&feature=youtu.be. Accessed 7 June 2023.
“Java – How to Use EditText User Input, Increment and Decrement Buttons Values in Android – ITecNote.” Itecnote.com, itecnote.com/tecnote/java-how-to-use-edittext-user-input-increment-and-decrement-buttons-values-in-android/. Accessed 7 June 2023.
"Horton, J., 2019. Android Programming with Beginers. 1 ed. Birmingham: Packt Publishing Ltd."
"Agency, T., 2022. How to Save Image to Firebase Storage in Android Studio using Kotlin | Kotlin tutorial 2022 in hindi. [Online]
Available at: https://www.youtube.com/watch?v=mHoBIwR2__0&t=298s
[Accessed 01 June 2023].
Used ChatGPT for debugging and Fixing Alignment
*/

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.database.*

class AddCollectionShoe : AppCompatActivity() {
    // Declarations
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_collection_shoe)
/*
        // Initialize spinners
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        databaseReference = FirebaseDatabase.getInstance().reference
        // Create an ArrayAdapter for spinner1 and set the retrieved data as its values
        databaseReference.child("Sneakers").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val dataList1 = dataSnapshot.children.mapNotNull { it.getValue(String::class.java) }
                // Create an ArrayAdapter for spinner1 and set the retrieved data as its values
                val adapter1 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, dataList1)
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner1.adapter = adapter1
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Log any errors that occur while retrieving data
                Log.e("AddCollectionShoe", "Database error: ${databaseError.message}")
            }
        })
        // Retrieve data from "Collections" node in Firebase Database
        databaseReference.child("Collections").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Create a list to store the retrieved values
                val dataList2 = dataSnapshot.children.mapNotNull { it.getValue(String::class.java) }
                // Create an ArrayAdapter for spinner2 and set the retrieved data as its values
                val adapter2 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, dataList2)
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter2
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Log any errors that occur while retrieving data
                Log.e("AddCollectionShoe", "Database error: ${databaseError.message}")
            }
        })*/
    }
}

