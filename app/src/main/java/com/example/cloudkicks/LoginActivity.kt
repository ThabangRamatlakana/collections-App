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
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cloudkicks.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener{
            // Get the email and password from the input fields
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            // Check if both email and password are not empty
            if(email.isNotEmpty() && password.isNotEmpty()){
                // Attempt to sign in with the provided email and password using FirebaseAuth
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    // Check if the sign-in operation is successful
                    if (it.isSuccessful){
                        // Create an Intent to navigate to the MainActivity
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }else{
               Toast.makeText(this,"All fields must be populated",Toast.LENGTH_SHORT).show()
            }
        }
        //sets a click listener on the signupRedirect view.
        // The setOnClickListener function takes a lambda expression as its parameter,
        // which will be invoked when the view is clicked.
        binding.signupRedirect.setOnClickListener{
            // Create an Intent to navigate to the SignupActivity
            val signupIntent = Intent(this,SignupActivity::class.java)
            // Start the SignupActivity by launching the signupIntent
            startActivity(signupIntent)
        }
    }
}