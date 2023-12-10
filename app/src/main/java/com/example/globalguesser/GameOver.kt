package com.example.globalguesser

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
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

    // challenge button
    private lateinit var challengeButton : Button

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

        challengeButton = findViewById(R.id.send_email)

        // show the user the current time
        if(score == 5)
            currTimeTV.text = "" + currTime + " secs"
        else
            currTimeTV.text = "Ran out of time!!"

        // show the user the best time
        if(score == 5 && currTime < bestTime) {
            // new best time

            // show challenge button
            challengeButton.visibility = View.VISIBLE

            // display time
            bestTimeTV.text = "" + currTime + " secs"
            bestTimeTV.setTextColor(Color.parseColor("#36ba57"))
            bestTimeTV.setTypeface(null, Typeface.BOLD);

            // update best time
            game.setBestTime(currTime) // in model
            sharedPreferences.edit().putLong("bestTime", currTime).commit() // in persistent data
        } else if(bestTime == 100L) {
            bestTimeTV.text = "None"
            bestTimeTV.setTextColor(Color.BLACK)

            challengeButton.visibility = View.INVISIBLE
        } else {
            bestTimeTV.text = "" + bestTime + " secs"
            bestTimeTV.setTextColor(Color.BLACK)

            challengeButton.visibility = View.INVISIBLE
        }
    }

    // Send email of results
    fun sendEmail(v : View){
        var high_score_message : String =
            "🌍 Challenge Alert! 🚩" +
                    "\n\nThink you know your flags? " +
                    "Test your skills in the Globe Guesser game! 🏁" +
                    "\n\nGuess the country based on its flag and climb the leaderboard! 📈💪 " +
                    "\n\nCan you beat my best time of ${game.getBestTime()} seconds? 🤔🌐" +
                    "\n\nJoin the fun now! #GlobeGuesserChallenge"

        var emailIntent : Intent = Intent(Intent.ACTION_SENDTO)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[GLOBE GUESSER] Can you beat my time?!")
        emailIntent.putExtra(Intent.EXTRA_TEXT, high_score_message)
        emailIntent.setData(Uri.parse("mailto:"))

        // so when the user returns, the game appears
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(emailIntent)
    }

    // Go back to the home screen when user clicks play again
    fun modifyView(v : View){
        var intent : Intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }
}
