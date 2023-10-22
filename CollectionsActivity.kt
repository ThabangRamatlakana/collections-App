package com.example.cloudkicks

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

    private lateinit var collectionName: EditText
    private lateinit var decrease: Button
    private lateinit var increase: Button
    private lateinit var integer_number: TextView
    private lateinit var collectionDescription: EditText
    private lateinit var clearButton: Button
    private lateinit var uploadImage: ImageView
    private lateinit var saveButton: Button
    private lateinit var uri: Uri
    private lateinit var mediaGridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        collectionName = findViewById(R.id.collectionName)
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
            decrementQuantity()
        }

        increase.setOnClickListener {
            incrementQuantity()
        }

        saveButton.setOnClickListener {
            saveData()
        }

        clearButton.setOnClickListener {
            clearForm()
        }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
    }

    private fun saveData() {
        val uri: Uri = uri

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
                val collectionName = collectionName.text.toString()
                val collectionDescription = collectionDescription.text.toString()
                val collectionGoal = integer_number.text.toString()

                val collectionData = HashMap<String, Any>()
                collectionData["collectionName"] = collectionName
                collectionData["collectionDescription"] = collectionDescription
                collectionData["collectionGoal"] = collectionGoal
                collectionData["imageUrlCollection"] = imageUrlCollection

                sneakerId?.let { databaseReference.child(it).setValue(collectionData) }
                    ?.addOnCompleteListener { task: Task<Void?> ->
                        dialog.dismiss()
                        if (task.isSuccessful) {
                            Toast.makeText(this@CollectionsActivity, "Data uploaded successfully", Toast.LENGTH_SHORT).show()

                            // Call the addToCollection() method here
                            addToCollection(collectionName, collectionGoal, collectionDescription)

                            // Retrieve the data from Firebase Realtime Database
                            val collectionRef = FirebaseDatabase.getInstance().reference.child("Collections")
                            collectionRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val mediaFolderList = mutableListOf<String>()
                                    for (snapshot in dataSnapshot.children) {
                                        val collection = snapshot.getValue(CollectionClass::class.java)
                                        if (collection != null) {
                                            val imageUrlCollection = collection.imageUrlCollection
                                            if (imageUrlCollection != null) {
                                                mediaFolderList.add(imageUrlCollection)
                                            }
                                        }
                                    }

                                    val adapter = ArrayAdapter(this@CollectionsActivity, android.R.layout.simple_list_item_1, mediaFolderList)
                                    mediaGridView.adapter = adapter
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Handle the error case
                                }
                            })

                            // Go back to the CollectionsActivity instead of starting MainActivity
                            onBackPressed()
                        } else {
                            Toast.makeText(this@CollectionsActivity, "Failed to upload data", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun incrementQuantity() {
        val currentQuantity = integer_number.text.toString().toInt()
        val newInteger = currentQuantity + 1
        integer_number.text = newInteger.toString()
    }

    private fun decrementQuantity() {
        val currentInteger = integer_number.text.toString().toInt()
        if (currentInteger > 0) {
            val newInteger = currentInteger - 1
            integer_number.text = newInteger.toString()
        }
    }

    private fun addToCollection(collectionName: String, quantity: String, description: String) {
        // Assuming you have a database connection or reference
        val databaseReference = FirebaseDatabase.getInstance().reference.child("YourCollectionTable")

        // Create a unique key for the new entry
        val collectionId = databaseReference.push().key

        // Create a HashMap to store the collection data
        val collectionData = HashMap<String, Any>()
        collectionData["collectionName"] = collectionName
        collectionData["quantity"] = quantity
        collectionData["description"] = description

        // Save the data to the database using the generated key
        collectionId?.let { databaseReference.child(it).setValue(collectionData) }
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Data saved successfully, perform any additional actions or show a message
                    // For example, display a toast message
                    Toast.makeText(this, "Data saved to collection", Toast.LENGTH_SHORT).show()
                } else {
                    // Error occurred while saving data, handle the failure case
                    // For example, display an error message
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun clearForm() {
        collectionName.text.clear()
        integer_number.text = "0"
        collectionDescription.text.clear()
        uploadImage.setImageResource(R.drawable.uploadimagevector) // Set your default image resource here
    }
}
