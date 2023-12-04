package com.example.globalguesser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var difficultyLevel: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerId = findViewById<Spinner>(R.id.difficulty)
        val playButtonID = findViewById<Button>(R.id.play)
        val difficulty  = arrayOf("Easy" ,"Medium","Hard")
        val arrayadp = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, difficulty)
        spinnerId.adapter = arrayadp
        spinnerId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                difficultyLevel = difficulty[position];
                Toast.makeText(this@MainActivity, "difficulty is ${difficulty[position]}", Toast.LENGTH_LONG)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                difficultyLevel = difficulty[0];
                Toast.makeText(this@MainActivity, "difficulty is  easy", Toast.LENGTH_LONG)
            }
        }
    }
    
    fun modifyView( v: View){
        var intent : Intent = Intent (this, GameActivity::class.java)
        startActivity(intent)
    }
}