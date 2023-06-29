package com.example.minorproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        btnUpdatePass.visibility = View.INVISIBLE
        edtCPassword.visibility = View.INVISIBLE
        edtPassword.visibility= View.INVISIBLE

        btnCheckUname.setOnClickListener {
            val uname=Uname.text.toString()
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
                                Toast.makeText(applicationContext,"Username Matched", Toast.LENGTH_LONG).show()
                                btnUpdatePass.visibility = View.VISIBLE
                                edtCPassword.visibility = View.VISIBLE
                                edtPassword.visibility= View.VISIBLE
                                Toast.makeText(applicationContext,"Can Change the Password Now", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        btnUpdatePass.setOnClickListener {
            val uname=Uname.text.toString()
            var password = edtPassword.text.toString()
            val cpassword = edtCPassword.text.toString()
            val db = FirebaseDatabase.getInstance().getReference("User")
            if (cpassword == password)
            {
                updateData(uname,password)
            }

        }
    }
    private fun updateData(uname: String, password: String)
    {
        val db = FirebaseDatabase.getInstance().getReference("User")
        val user = mapOf<String,String>(
            "uname" to uname,
            "password" to password
        )
        db.child(uname).updateChildren(user).addOnSuccessListener {
            Uname.setText("")
            edtPassword.setText("")
            edtCPassword.setText("")
            Toast.makeText(applicationContext,"Updated Successfully", Toast.LENGTH_LONG).show()
        }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Not Updated", Toast.LENGTH_LONG).show()
            }
    }
}