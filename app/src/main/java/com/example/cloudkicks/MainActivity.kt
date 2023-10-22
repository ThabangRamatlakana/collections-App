package com.example.cloudkicks
/*
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.widget.Button
import java.util.ArrayList
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudkicks.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.zip.DataFormatException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var AddShoeButton: Button
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter

    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CloudKicks)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recycleView
        recyclerView?.layoutManager = GridLayoutManager(this, 2)

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = MyAdapter(this@MainActivity, dataList)
        recyclerView?.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("cloudKicks")
        dialog.show()

        eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapShot in snapshot.children) {
                    val dataClass = itemSnapShot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        }

        databaseReference?.addValueEventListener(eventListener as ValueEventListener)

        AddShoeButton = binding.AddShoeButton
        AddShoeButton.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_collection -> {
                    val intent = Intent(this@MainActivity, CollectionView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Sneakers")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (snapshot in dataSnapshot.children) {
                    val data = snapshot.getValue(DataClass::class.java)
                    if (data != null) {
                        dataList.add(data)
                    }
                }
                recyclerView?.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors here
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the event listener when the activity is destroyed
        eventListener?.let { databaseReference?.removeEventListener(it) }
    }
}
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.content.Intent
import android.widget.Button
import java.util.ArrayList
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloudkicks.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.zip.DataFormatException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var AddShoeButton:Button
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dataList:ArrayList<DataClass>
    private lateinit var adapter: MyAdapter

    var databaseReference: DatabaseReference? = null
    var eventListener:ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CloudKicks)

        // Inflate the layout using the generated binding class, ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Create a GridLayoutManager with one column for the RecyclerView
        val gridLayoutManager = GridLayoutManager(this@MainActivity,1)
        binding.recycleView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = MyAdapter(this@MainActivity,dataList)
        binding.recycleView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("cloudKicks")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for(itemSnapShot in snapshot.children){
                    val dataClass = itemSnapShot.getValue(DataClass::class.java)
                    if (dataClass != null){
                        dataList.add((dataClass))
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
               dialog.dismiss()
            }

        })

        AddShoeButton = findViewById(R.id.AddShoeButton)
        AddShoeButton.setOnClickListener {
            //activity to open another activity when button is clicked
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    // Create an intent to navigate to MainActivity
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    // Start the MainActivity by launching the intent
                    startActivity(intent)
                    // Return 'true' to indicate that the menu item click event has been handled
                    true
                }
                R.id.action_collection -> {
                    // Create an intent to navigate to CollectionView activity
                    val intent = Intent(this@MainActivity, CollectionView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}*/

