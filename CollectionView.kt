package com.example.cloudkicks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CollectionView : AppCompatActivity() {

    private lateinit var collectionViewfab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mediaGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_view)

        mediaGridView = findViewById(R.id.mediaGridView)

        collectionViewfab = findViewById(R.id.collectionViewfab)
        collectionViewfab.setOnClickListener {
            val intent = Intent(this, CollectionsActivity::class.java)
            startActivity(intent)
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    // Create an intent to navigate to MainActivity
                    val intent = Intent(this@CollectionView, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_collection -> {
                    // Create an intent to navigate to CollectionView activity
                    val intent = Intent(this@CollectionView, CollectionView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
}