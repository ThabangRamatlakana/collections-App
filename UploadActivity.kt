package com.example.cloudkicks

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
class UploadActivity : AppCompatActivity() {

    private lateinit var uploadImage: ImageView
    private lateinit var saveButton: Button
    private lateinit var uploadImageName: EditText
    private lateinit var describeMoment: EditText
    private lateinit var uploadPrice: EditText
    private var imageUrl: String = ""
    private lateinit var uri: Uri
    private lateinit var floatButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadImage = findViewById(R.id.uploadImage)
        saveButton = findViewById(R.id.saveButton)
        uploadImageName = findViewById(R.id.uploadImageName)
        describeMoment = findViewById(R.id.describe_moment)
        uploadPrice = findViewById(R.id.uploadPrice) // Uncomment this line

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data ?: run {
                        Toast.makeText(this@UploadActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
                        return@registerForActivityResult
                    }
                    uploadImage.setImageURI(uri)
                } else {
                    Toast.makeText(this@UploadActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }

        uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("Sneaker Images")
            .child(uri.lastPathSegment.toString())
        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val uri: Uri = uri  // Add this line to declare the variable uri

        storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnCompleteListener { uriTask ->
                val imageUrl = uriTask.result.toString()

                // Save other data to Firebase Realtime Database
                val databaseReference = FirebaseDatabase.getInstance().reference.child("Sneakers")
                val sneakerId = databaseReference.push().key
                val uploadName = uploadImageName.text.toString()
                val momentDescription = describeMoment.text.toString()
                val uploadPrice = uploadPrice.text.toString()

                val sneakerData = HashMap<String, Any>()
                sneakerData["uploadName"] = uploadName
                sneakerData["momentDescription"] = momentDescription
                sneakerData["uploadPrice"] = uploadPrice
                sneakerData["imageUrl"] = imageUrl

                sneakerId?.let { databaseReference.child(it).setValue(sneakerData) }
                    ?.addOnCompleteListener { task: Task<Void?> ->
                        dialog.dismiss()
                        if (task.isSuccessful) {
                            Toast.makeText(this@UploadActivity, "Data uploaded successfully", Toast.LENGTH_SHORT).show()
                            // Start the main activity
                            val intent = Intent(this@UploadActivity, MainActivity::class.java)
                            startActivity(intent)
                           // finish() // Optional: Finish the current activity to prevent going back to it
                        } else {
                            Toast.makeText(this@UploadActivity, "Failed to upload data", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    // Function to clear all fields
    fun clearFields(view: View) {
        uploadImage.setImageURI(null)
        uploadImageName.text.clear()
        describeMoment.text.clear()
        uploadPrice.text.clear()
    }

    // Function to cancel the upload
    fun cancelUpload(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
