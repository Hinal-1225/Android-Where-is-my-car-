package com.example.minorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            val uname=Uname.text.toString()
            val password=Password.text.toString()
            val db= FirebaseDatabase.getInstance().getReference("User")

            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        for (usersnap in snapshot.children)
                        {
                            val user = usersnap.getValue(User::class.java)
                            if(uname == user!!.uname)
                            {
                                if (password == user.password)
                                {
                                    var prefer=getSharedPreferences("MyPref", MODE_PRIVATE)
                                    var editor=prefer.edit()
                                    editor.putString("UserName",uname)
                                    editor.commit()
                                    Toast.makeText(applicationContext,"Login Successfully", Toast.LENGTH_LONG).show()
                                    val intent = Intent(applicationContext,ParkMapActivity::class.java)
                                    startActivity(intent)
                                }
                                else{
                                    Toast.makeText(applicationContext,"Password Incorrect", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        forgot_password.setOnClickListener {
            val intent = Intent(this,ForgetPassword::class.java)
            startActivity(intent)
        }

    }
}