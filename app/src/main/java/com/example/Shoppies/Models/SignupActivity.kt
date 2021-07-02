package com.example.Shoppies.Models

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Shoppies.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class SignupActivity : AppCompatActivity() {

    lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup)

        auth= FirebaseAuth.getInstance()

        registerbt.setOnClickListener {
            registeruser()
        }
    }


    private fun registeruser(){
        val email=signemailtv.text.toString()
        val password=signpasstv.text.toString()



        if(email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main) {
                        currentlyLoggedIn()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignupActivity,e.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }

    private fun currentlyLoggedIn(){
        val user=auth.currentUser

         if(user != null){
             val intent= Intent(this, MainActivity::class.java)
             startActivity(intent)
         }
    }


}