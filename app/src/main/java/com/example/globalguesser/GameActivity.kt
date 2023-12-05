package com.example.globalguesser

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    private lateinit var countdownText:TextView
    private lateinit var countDownTimer:CountDownTimer
    private var timeLeftInMilliseconds:Long = 6000;

    // list of flags (for order)
    private lateinit var easyFlagsOrder : ArrayList<String> // ["us_flag",...]
    private lateinit var mediumFlagsOrder : ArrayList<String>
    private lateinit var hardFlagsOrder : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        // timer
        countdownText = findViewById(R.id.timer)
        startTimer()

        // flags
        for(e in MainActivity.easyFlags.keys){
            easyFlagsOrder.add(e)
        }

        for(m in MainActivity.mediumFlags.keys){
            mediumFlagsOrder.add(m)
        }

        for(h in MainActivity.hardFlags.keys){
            hardFlagsOrder.add(h)
        }

        // from this order, depending on difficulty, show the next flags
        if (MainActivity.difficultyLevel == "Easy"){

            for(i in easyFlagsOrder){
                val flagToShow : ImageView = findViewById(resources.getIdentifier(i, "id", packageName))
                flagToShow.visibility = ImageView.VISIBLE // shows flag

                // flagToShow.visiblity = ImageView.GONE // to hide it
            }
        } else if(MainActivity.difficultyLevel == "Medium"){
            for(i in mediumFlagsOrder){
                val flagToShow : ImageView = findViewById(resources.getIdentifier(i, "id", packageName))
                flagToShow.visibility = ImageView.VISIBLE // shows flag

                // flagToShow.visiblity = ImageView.GONE // to hide it
            }
        } else {
            for(i in hardFlagsOrder){
                val flagToShow : ImageView = findViewById(resources.getIdentifier(i, "id", packageName))
                flagToShow.visibility = ImageView.VISIBLE // shows flag

                // flagToShow.visiblity = ImageView.GONE // to hide it
            }
        }

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