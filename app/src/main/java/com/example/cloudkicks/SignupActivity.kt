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