package com.example.globalguesser

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class Game {
    //model

    // for game screen
    private lateinit var currFlag : String

    // for game over screen
    private var numFlagsGuessed : Int = 0 // counts num of flags guessed so far (for progress bar)
    private var currentTime : Long = 1 // pull from GameActivity
    private var bestTime : Long = 0 // pull from sharedPreferences

    constructor(currFlag : String, bestTime : Long){
        // pull from view
        this.currFlag = currFlag
        this.bestTime = bestTime
    }

    fun getCurrentTime() : Long {
        return currentTime
    }


    fun getBestTime() : Long {
        return bestTime
    }

    fun getNumFlagsGuessed() : Int {
        return numFlagsGuessed
    }

    fun setCurrentTime(curTime : Long) {
        currentTime = curTime
    }

    fun setCurrFlag(currFlag : String) {
        this.currFlag = currFlag
    }

    // check whether the guess matches the flag
    fun isGuessCorrect(guess : String) : Boolean {
        // process guess
        var guessProcessed = guess.lowercase().trim()
        return if(guessProcessed == currFlag){
            //correct (controller updates view to next flag & clears editText)
            updateNumFlagsGuessed()
            true
        } else {
            //incorrect (controller clears editText for re-guess)
            false
        }
    }

    private fun updateNumFlagsGuessed() : Int {
        numFlagsGuessed++
        return numFlagsGuessed // controller uses this to see if game is over
    }

}
