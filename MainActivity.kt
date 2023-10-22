package com.example.cloudkicks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.content.Intent
import java.util.ArrayList
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloudkicks.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.util.zip.DataFormatException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dataList:ArrayList<DataClass>
    private lateinit var adapter: MyAdapter

    var databaseReference: DatabaseReference? = null
    var eventListener:ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CloudKicks)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    // Create an intent to navigate to MainActivity
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
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
}

