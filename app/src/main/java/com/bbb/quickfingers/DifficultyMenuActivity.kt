package com.bbb.quickfingers

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class DifficultyMenuActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    //    var intentToBeUsed = Intent(applicationContext, GameActivity::class.java)
    var difficulty = "Hard"
    var time ="2 Minutes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_menu)

        val timeSpinner: Spinner = findViewById(R.id.timeSpinner)
        val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        timeSpinner.onItemSelectedListener = this
        difficultySpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            timeSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.difficulty_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            difficultySpinner.adapter = adapter
        }

    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (parent.id == R.id.timeSpinner) {
            // time spinner selected
            time = parent.getItemAtPosition(pos).toString()

        } else if (parent.id == R.id.difficultySpinner) {
            // difficulty spinner selected
            difficulty = parent.getItemAtPosition(pos).toString()

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    fun startGame(view: View){
        val intent = Intent(applicationContext, GameActivity::class.java)
        intent.putExtra("time", time)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
        finish()
    }

    fun openMainMenu(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()

    }





}