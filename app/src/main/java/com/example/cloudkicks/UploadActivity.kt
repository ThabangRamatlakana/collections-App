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
    private var imageUrl: String = ""
    private lateinit var uri: Uri
    private lateinit var floatButton: FloatingActionButton
    private lateinit var spinner: Spinner
    private lateinit var clearButton:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        uploadImage = findViewById(R.id.uploadImage)
        saveButton = findViewById(R.id.saveButton)
        uploadImageName = findViewById(R.id.uploadImageName)
        describeMoment = findViewById(R.id.describe_moment)
        spinner = findViewById(R.id.spinner_shoe_sizes)

        val placeholderDrawable = resources.getDrawable(R.drawable.cameraicon)


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.shoe_sizes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedShoeSize = spinner.getItemAtPosition(position).toString()
                // Use the selectedShoeSize as needed
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected (optional)
            }
        }

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
        val clearButton: Button = findViewById(R.id.clearButton)
        clearButton.setOnClickListener {
            val shoeSizeSpinner: Spinner = findViewById(R.id.spinner_shoe_sizes)
            val imageView: ImageView = findViewById(R.id.uploadImage)
            val describe_Moment: EditText= findViewById(R.id.describe_moment)
            imageView.setImageDrawable(placeholderDrawable)

            uploadImageName.setText("")
            describe_Moment.setText("")
            shoeSizeSpinner.setSelection(0)
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
                val uploadSize = spinner.selectedItem.toString()

                val sneakerData = HashMap<String, Any>()
                sneakerData["uploadName"] = uploadName
                sneakerData["momentDescription"] = momentDescription
                sneakerData["uploadSize"] = uploadSize
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
        spinner.setSelection(0)
    }

    // Function to cancel the upload
    fun cancelUpload(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
