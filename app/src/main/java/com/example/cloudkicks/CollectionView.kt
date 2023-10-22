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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CollectionView : AppCompatActivity() {

    private lateinit var AddCollectionButton: Button
    private lateinit var AddShoeCollectionButton : Button
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mediaGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_view)

        //we were trying to create a mediaView so we can display all the collections

        mediaGridView = findViewById(R.id.mediaGridView)

        AddCollectionButton = findViewById(R.id.AddCollectionButton)
        AddShoeCollectionButton = findViewById(R.id.AddShoeCollectionButton)

        AddCollectionButton.setOnClickListener {
            //creating an intent that will take to another page/activity
            val intent = Intent(this, CollectionsActivity::class.java)
            startActivity(intent)
        }

        AddShoeCollectionButton.setOnClickListener {
            //creating an intent that will take to another page/activity
            val intent = Intent(this, AddCollectionShoe::class.java)
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