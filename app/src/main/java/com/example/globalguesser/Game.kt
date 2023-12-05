package com.example.globalguesser

import android.content.Context
import android.graphics.Bitmap

class Game {
    //model

    private var difficulty : String = MainActivity.difficultyLevel

    // maps of correct pairs
    private val easyFlags = MainActivity.easyFlags
    private val mediumFlags = MainActivity.mediumFlags
    private val hardFlags = MainActivity.hardFlags

    // for game screen
    private var flagId : String
    private var guess : String

    // for game over screen
    private var numFlagsGuessed : Int = 0 // counts num of flags guessed so far (for progress bar)
    private var currentTime : Long = 1 // pull from GameActivity
    private var bestTime : Long = 0 // pull from sharedPreferences

    constructor(context : Context, difficulty : String, flagId: String, guess : String){
        // pull from view
        this.difficulty = difficulty
        this.flagId = flagId
        this.guess = guess

        // code for persistent data: bestTime
    }

    fun getCurrentTime() : Long {
        return currentTime
    }

    fun getBestTime() : Long {
        return bestTime
    }

    fun getFlagId() : String {
        return flagId
    }

    fun getGuess() : String {
        return guess
    }

    fun getNumFlagsGuessed() : Int {
        return numFlagsGuessed
    }

    // check whether the guess matches the flag
    fun isGuessCorrect() : Boolean {
        // process guess
        var guessProcessed = guess.lowercase().trim()

        if(difficulty == "Easy"){
            if(guess == easyFlags.getValue(flagId)){
                //correct (controller updates view to next flag & clears editText)
                updateNumFlagsGuessed()
                return true
            } else {
                //incorrect (controller clears editText for re-guess)
                return false
            }
        } else if(difficulty == "Medium") {
            if(guess == mediumFlags.getValue(flagId)){
                //correct (controller updates view to next flag & clears editText)
                updateNumFlagsGuessed()
                return true
            } else {
                //incorrect (controller clears editText for re-guess)
                return false
            }
        } else {
            if(guess == hardFlags.getValue(flagId)){
                //correct (controller updates view to next flag & clears editText)
                updateNumFlagsGuessed()
                return true
            } else {
                //incorrect (controller clears editText for re-guess)
                return false
            }
        }
    }

    private fun updateNumFlagsGuessed() : Int {
        numFlagsGuessed++
        return numFlagsGuessed // controller uses this to see if game is over
    }

}