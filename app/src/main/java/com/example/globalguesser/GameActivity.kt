package com.example.globalguesser

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    // variables for the timer
    private lateinit var countdownText:TextView
    private lateinit var countDownTimer:CountDownTimer
    private lateinit var progressBar:ProgressBar;
    private var currentProgress:Int = 0;
    private var timeLeftInMilliseconds:Long = 60000

    // list of flags
    private lateinit var flagMap : HashMap<String, Int>
    private var flags : ArrayList<String> = ArrayList()

    private lateinit var flagImage : ImageView
    private lateinit var flagText : EditText
    private var currFlagIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)
        // timer
        countdownText = findViewById(R.id.timer)
        startTimer()

        // set progress bar
        progressBar = findViewById(R.id.progress_bar)
        progressBar.max = 5;

        // views
        flagImage = findViewById(R.id.flag_image)
        flagText = findViewById(R.id.flag_text)

        // store persistent data
        sharedPreferences = this.getSharedPreferences("Game", Context.MODE_PRIVATE)

        // get the right flags based on difficulty level
        flagMap = when (MainActivity.difficultyLevel) {
            "Easy" -> {
                MainActivity.easyFlags
            }

            "Medium" -> {
                MainActivity.mediumFlags
            }

            else -> {
                MainActivity.hardFlags
            }
        }
        for(e in flagMap.keys) {
            flags.add(e)
        }

        // set the first flag
        flagMap[flags[0]]?.let { flagImage.setBackgroundResource(it) }
        // initialize game variable
        game = Game(flags[0], sharedPreferences.getLong("bestTime", 100))
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
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

    // check if answer is correct when ENTER is clicked and update the progress bar
    fun checkAnswer(v : View) {
        if(game.isGuessCorrect(flagText.text.toString())) {
            flagText.setText("")
            flags.removeAt(currFlagIndex)

            //Updating the progress bar
            currentProgress = ++currentProgress
            progressBar.progress = currentProgress

            if (flags.isEmpty() || flags.size < 1)
                modifyData()
            else if (currFlagIndex > flags.size - 1) {
                game.setCurrFlag(flags[currFlagIndex])
                flagMap[flags[currFlagIndex]]?.let { flagImage.setBackgroundResource(it) }
            } else {
                game.setCurrFlag(flags[currFlagIndex])
                flagMap[flags[currFlagIndex]]?.let { flagImage.setBackgroundResource(it) }
            }
        } else {
            flagText.setText("")
            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_LONG).show()
        }
    }

    // move to a new flag when SKIP is clicked
    fun skipFlag(v : View) {
        if(game.getNumFlagsGuessed() == 5) {
            modifyData()
        } else if(flags.size == 1) {
            Toast.makeText(this, "Last Remaining Flag", Toast.LENGTH_LONG).show()
        } else if(currFlagIndex == flags.size - 1) {
            currFlagIndex = 0
            game.setCurrFlag(flags[currFlagIndex])
            flagMap[flags[currFlagIndex]]?.let { flagImage.setBackgroundResource(it) }
        } else {
            currFlagIndex += 1
            game.setCurrFlag(flags[currFlagIndex])
            flagMap[flags[currFlagIndex]]?.let { flagImage.setBackgroundResource(it) }
        }
    }

    private fun updateTimer() {
        val minutes = (timeLeftInMilliseconds / 1000) / 60
        val seconds = (timeLeftInMilliseconds / 1000) % 60

        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        countdownText.text = timeLeftFormatted
    }


    fun modifyData(){
        // store how long it took to finish this round
        game.setCurrentTime(60 - (timeLeftInMilliseconds / 1000))

        // jump to game over screen
        val intent : Intent = Intent (this, GameOver::class.java)
        startActivity(intent)
    }

    companion object {
        lateinit var game : Game
        lateinit var sharedPreferences : SharedPreferences
    }
}
