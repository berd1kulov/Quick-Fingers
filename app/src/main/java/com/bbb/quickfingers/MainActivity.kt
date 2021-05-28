package com.bbb.quickfingers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity()  {
    var bgSound = "no"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.buSoundOn).visibility = View.GONE
        findViewById<Button>(R.id.buSoundOff).visibility = View.VISIBLE

    }


    fun openDifficultyMenu(view: View){
        val intent =  Intent(applicationContext, DifficultyMenuActivity::class.java)
        intent.putExtra("sound",bgSound)
        startActivity(intent)
        finish()

    }


    fun openHighScores(view: View){
        val intent = Intent(applicationContext, HighScoresActivity::class.java)
        intent.putExtra("time", "1 Minute")
        intent.putExtra("difficulty", "Easy")
        startActivity(intent)
        finish()

    }

    fun buSoundOff(view: View) {
        bgSound = "yes"
        startService(Intent(this,BackgroundSound::class.java))
        findViewById<Button>(R.id.buSoundOff).visibility = View.GONE
        findViewById<Button>(R.id.buSoundOn).visibility = View.VISIBLE

    }

    fun buSoundOn(view: View) {
        bgSound = "no"
        stopService(Intent(this,BackgroundSound::class.java))
        findViewById<Button>(R.id.buSoundOn).visibility = View.GONE
        findViewById<Button>(R.id.buSoundOff).visibility = View.VISIBLE
    }

    fun buInfo(view: View) {
        MyCustomDialog().show(supportFragmentManager, "MyCustomFragment")
    }


}