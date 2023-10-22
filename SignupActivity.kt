package com.example.cloudkicks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cloudkicks.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private  lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener{
            val userName = binding.signupEmail.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirmPassword.text.toString()

            if(email.isNotEmpty()&& password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent (this,LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this,"All fields must be populated",Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginRedirect.setOnClickListener{
            val loginIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}