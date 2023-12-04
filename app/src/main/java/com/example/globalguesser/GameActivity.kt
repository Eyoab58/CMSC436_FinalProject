package com.example.globalguesser

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var  countdownText:TextView
    private lateinit var countDownTimer:CountDownTimer
    private var timeLeftInMilliseconds:Long = 6000;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        countdownText = findViewById(R.id.timer)
        startTimer()
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timeLeftInMilliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMilliseconds = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                countdownText.text = "Time's Up!"
                modifyData()
            }
        }.start()
    }

    private fun updateTimer() {
        val minutes = (timeLeftInMilliseconds / 1000) / 60
        val seconds = (timeLeftInMilliseconds / 1000) % 60

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        countdownText.text = timeLeftFormatted
    }


    fun modifyData(){
        var intent : Intent = Intent (this, GameOver::class.java)
        startActivity(intent)
    }
}