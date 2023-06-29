package com.example.minorproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat.setAlarmClock
import kotlinx.android.synthetic.main.activity_timing.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timing)

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        btnArrivalTime.text = currentDateTimeString

        btnLetsGo.setOnClickListener {
            val intent = Intent(this, EndMapActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        if(id==R.id.item1)
        {
            var prefer=getSharedPreferences("MyPref", MODE_PRIVATE)
            var editor=prefer.edit()
            editor.clear()
            editor.commit()
            var intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext,"Logout successfully", Toast.LENGTH_LONG).
            show()
        }
        else if(id==R.id.item2)
        {
            val intent = Intent(this,FeedbackActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}