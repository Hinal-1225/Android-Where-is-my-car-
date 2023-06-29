package com.example.minorproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        btnfeedback.setOnClickListener {
            /*val feedback = edtfeedback.text.toString()
            val db = FirebaseDatabase.getInstance().getReference("Feedback")
            db.child(feedback).setValue(feedback).addOnSuccessListener {
                Toast.makeText(applicationContext, "Location Saved", Toast.LENGTH_LONG).show()
            }*/
            edtfeedback.setText("")
            Toast.makeText(this, "Thank You for Your Feedback", Toast.LENGTH_SHORT).show()
        }
    }
}