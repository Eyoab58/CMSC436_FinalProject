package com.example.globalguesser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

    }

    fun modifyView( v: View){
        var intent : Intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }
}