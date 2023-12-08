package com.example.globalguesser

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    // Current Time and Best Time text views
    private lateinit var currTimeTV : TextView
    private lateinit var bestTimeTV : TextView

    // data from the game
    private lateinit var game : Game
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // when timer is done
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)

        game = GameActivity.game
        sharedPreferences = GameActivity.sharedPreferences

        var score : Int = game.getNumFlagsGuessed()
        var bestTime : Long = game.getBestTime()
        var currTime : Long = game.getCurrentTime()

        currTimeTV = findViewById(R.id.finished_time)
        bestTimeTV = findViewById(R.id.best_time)

        // show the user the current time
        if(score == 5)
            currTimeTV.text = "" + currTime + "secs"
        else
            currTimeTV.text = "Ran out of time!!"

        // show the user the best time
        if(score == 5 && currTime < bestTime) {
            bestTimeTV.text = "" + currTime + "secs"
            sharedPreferences.edit().putLong("bestTime", currTime).commit()
        } else if(bestTime == 100L)
            bestTimeTV.text = "None"
        else
            bestTimeTV.text = "" + bestTime + "secs"
    }

    // Go back to the home screen when user clicks play again
    fun modifyView(v : View){
        var intent : Intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }
}
