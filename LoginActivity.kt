package com.example.cloudkicks

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
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }else{
               Toast.makeText(this,"All fields must be populated",Toast.LENGTH_SHORT).show()
            }
        }
        binding.signupRedirect.setOnClickListener{
            val signupIntent = Intent(this,SignupActivity::class.java)
            startActivity(signupIntent)
        }
    }
}