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

import com.example.cloudkicks.CollectionClass
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference



class CollectionsActivity : AppCompatActivity() {

    private lateinit var collectionsName: EditText
    private lateinit var decrease: Button
    private lateinit var increase: Button
    private lateinit var integer_number: TextView
    private lateinit var collectionDescription: EditText
    private lateinit var clearButton: Button
    private lateinit var uploadImage: ImageView
    private lateinit var saveButton: Button
    private lateinit var uri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        collectionsName = findViewById(R.id.collectionName)
        decrease = findViewById(R.id.decrease)
        increase = findViewById(R.id.increase)
        integer_number = findViewById(R.id.integer_number)
        collectionDescription = findViewById(R.id.collectionDescription)
        clearButton = findViewById(R.id.ClearButton)
        uploadImage = findViewById(R.id.uploadImage)
        saveButton = findViewById(R.id.saveButton)


        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data ?: run {
                        Toast.makeText(this@CollectionsActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    }
                    uploadImage.setImageURI(uri)
                } else {
                    Toast.makeText(this@CollectionsActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }

        decrease.setOnClickListener {
            //call the decrementQuantity method
            decrementQuantity()
        }

        increase.setOnClickListener {
            //call the incrementQuantity method
            incrementQuantity()
        }

        saveButton.setOnClickListener {
            //call the saveData method
            saveData()
        }

        clearButton.setOnClickListener {
            //call the clearForm method
            clearForm()
        }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("Sneaker Images")
            .child(uri.lastPathSegment.toString())
        val builder = AlertDialog.Builder(this@CollectionsActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog: AlertDialog = builder.create()
        dialog.show()


        storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnCompleteListener { uriTask ->
                val imageUrlCollection = uriTask.result.toString()

                // Save other data to Firebase Realtime Database
                val databaseReference = FirebaseDatabase.getInstance().reference.child("Collections")
                val sneakerId = databaseReference.push().key
                val collectionName = collectionsName.text.toString()
                val collectionDescription = collectionDescription.text.toString()
                val collectionGoal = integer_number.text.toString()

                val collectionData = HashMap<String, Any>()
                //assigns the value of the collectionName variable to the collectionData
                collectionData["collectionName"] = collectionName
                //assigns the value of the collectionDescription variable to the collectionData
                collectionData["collectionDescription"] = collectionDescription
                //assigns the value of the collectionGoal variable to the collectionData
                collectionData["collectionGoal"] = collectionGoal
                //assigns the value of the imageurlCollection variable to the collectionData
                collectionData["imageUrlCollection"] = imageUrlCollection


                sneakerId?.let { databaseReference.child(it).setValue(collectionData) }
                    ?.addOnCompleteListener { task: Task<Void?> ->
                        dialog.dismiss()
                        if (task.isSuccessful) {
                            Toast.makeText(this@CollectionsActivity, "Data uploaded successfully", Toast.LENGTH_SHORT).show()

                            // Start the Collection View Activity
                            val intent = Intent(this@CollectionsActivity, CollectionView::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@CollectionsActivity, "Failed to upload data", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    private fun incrementQuantity() {
        // Get the current quantity value from the TextView and convert it to an integer
        val currentQuantity = integer_number.text.toString().toInt()
        // Calculate the new integer value by adding 1 to the current quantity
        val newInteger = currentQuantity + 1
        // Update the TextView with the new integer value converted back to a string
        integer_number.text = newInteger.toString()
    }

    private fun decrementQuantity() {
        // Get the current integer value from the TextView
        val currentInteger = integer_number.text.toString().toInt()
        // Check if the current integer is greater than 0
        if (currentInteger > 0) {
            // Calculate the new integer value by subtracting 1
            val newInteger = currentInteger - 1
            // Update the TextView with the new integer value
            integer_number.text = newInteger.toString()
        }
    }

    //this method will be for clearing all the fields once the button is clicked
    private fun clearForm() {
        collectionsName.text.clear()
        integer_number.text = "0"
        collectionDescription.text.clear()
        val placeholderDrawable = resources.getDrawable(R.drawable.cameraicon)
        uploadImage.setImageDrawable(placeholderDrawable)// Set your default image resource here
    }

    }








