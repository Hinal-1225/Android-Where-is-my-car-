package com.example.minorproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            val fname = edtFname.text.toString()
            val lname = edtLname.text.toString()
            val uname = edtUname.text.toString()
            val phone = edtPhone.text.toString().toDouble()
            val password = edtPassword.text.toString()
            val cpassword = edtCPassword.text.toString()
            val user = User(fname, lname, uname, password, phone)
            val db = FirebaseDatabase.getInstance().getReference("User")

            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (usersnap in snapshot.children) {
                            val user1 = usersnap.getValue(User::class.java)
                            if (uname == user1!!.uname) {
                                Toast.makeText(applicationContext, "Please Enter a Unique Username", Toast.LENGTH_LONG).show()
                                edtUname.setText("")
                                val intent = Intent(applicationContext, RegisterActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else {
                                if (cpassword == password) {
                                    db.child(uname).setValue(user).addOnSuccessListener {
                                        Toast.makeText(applicationContext, "Register Successfully", Toast.LENGTH_LONG).show()
                                        edtFname.setText("")
                                        edtLname.setText("")
                                        edtUname.setText("")
                                        edtPhone.setText("")
                                        edtPassword.setText("")
                                        edtCPassword.setText("")
                                        val intent = Intent(applicationContext, ParkMapActivity::class.java)
                                        startActivity(intent)
                                    }
                                        .addOnFailureListener {
                                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                                        }
                                } else {
                                    Toast.makeText(applicationContext, "Password Does not match to Confirm Password", Toast.LENGTH_LONG).show()
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
    }
}