package com.bbb.quickfingers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*

class HighScoresActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    private var listHighScores = ArrayList<HighScore>()


    var time:String = "2 Minutes"
    var difficulty:String = "Hard"
    override fun onDestroy() {
        stopService(Intent(this,BackgroundSound::class.java))
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        val intent = intent
        if(intent.getStringExtra("sound").toString() == "on"){
            startService(Intent(this,BackgroundSound::class.java))
        }
        time = intent.getStringExtra("time").toString()
        difficulty = intent.getStringExtra("difficulty").toString()

        val timeSpinner: Spinner = findViewById(R.id.timeSpinner)
        val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)

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
        timeSpinner.setSelection(getPostitionInArray(time))
        difficultySpinner.setSelection(getPostitionInArray(difficulty))
        loadHighScores()

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        if (parent.id == R.id.timeSpinner) {
            // time spinner selected
            time = parent.getItemAtPosition(pos).toString()
            loadHighScores()
        } else if (parent.id == R.id.difficultySpinner) {
            // difficulty spinner selected
            difficulty = parent.getItemAtPosition(pos).toString()
            loadHighScores()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
    fun loadHighScores() {
        val myDatabase = this.openOrCreateDatabase("High_Scores", Context.MODE_PRIVATE,null)
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS highScores (id INTEGER PRIMARY KEY, username VARCHAR,difficulty INTEGER, time INTEGER, score INTEGER)")

        var timeMilliseconds:Int = formatTimeStringToMilliseconds(time)
        var difficultyMilliseconds:Int = formatDifficultyToMiliseconds(difficulty)

        val cursor = myDatabase!!.rawQuery("select * from highScores where time = $timeMilliseconds and difficulty=$difficultyMilliseconds ORDER BY score DESC LIMIT 10" , null)

        listHighScores.clear()
        if (cursor.moveToFirst()) {

            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))

                val username = cursor.getString(cursor.getColumnIndex("username"))
                val difficulty = cursor.getInt(cursor.getColumnIndex("difficulty"))
                val time = cursor.getInt(cursor.getColumnIndex("time"))
                val score = cursor.getInt(cursor.getColumnIndex("score"))


                listHighScores.add(HighScore(id, username,difficulty,time, score))

            } while (cursor.moveToNext())
        }

        var notesAdapter = HighScoresAdapter(this, listHighScores)
        findViewById<ListView>(R.id.lvHighScores).adapter = notesAdapter
    }

    inner class HighScoresAdapter : BaseAdapter {

        private var highScoresList = ArrayList<HighScore>()
        private var context: Context? = null

        constructor(context: Context, highScoresList: ArrayList<HighScore>) : super() {
            this.highScoresList = highScoresList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.highscore, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            var mHighScore = highScoresList[position]

            vh.tvUsername.text = mHighScore.username
            vh.tvScore.text = mHighScore.score.toString()



            return view
        }

        override fun getItem(position: Int): Any {
            return highScoresList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return highScoresList.size
        }
    }



    private class ViewHolder(view: View?) {
        val tvUsername: TextView
        val tvScore: TextView


        init {
            this.tvUsername = view?.findViewById(R.id.tvUsername) as TextView
            this.tvScore = view?.findViewById(R.id.tvScore) as TextView

        }
    }


    fun formatTimeMilliSecondsToString(timeMilliseconds: Int?): String {
        when (timeMilliseconds) {
            120000 ->  return "2 Minutes"
            60000 ->   return "1 Minute"
            30000 -> return "30 Seconds"
            15000 -> return "15 Seconds"

            else -> { // Note the block
                return "2 Minutes"
            }
        }
    }

    fun formatDifficultyMillisecondsToString(difficultyMilliseconds: Int?): String{
        when (difficultyMilliseconds) {
            200 ->  return "Hard"
            500 -> return "Normal"
            1000 -> return  "Easy"

            else -> { // Note the block
                return "Hard"
            }
        }


    }


    fun formatTimeStringToMilliseconds(timeString: String?):Int{
        when (timeString) {
            "2 Minutes" -> return 120000
            "1 Minute" ->  return 60000
            "30 Seconds" ->return  30000
            "15 Seconds" ->return  15000

            else -> { // Note the block
                return 120000
            }
        }
    }

    fun formatDifficultyToMiliseconds(difficultyString: String?): Int{
        when (difficultyString) {
            "Hard" ->    return 200
            "Normal" ->  return 500
            "Easy" ->    return 1000

            else -> { // Note the block
                return 200
            }
        }
    }

    fun openMainMenuu(view: View){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun openDifficultyMenu(view: View){
        val intent = Intent(applicationContext, DifficultyMenuActivity::class.java)
        intent.putExtra("time", "2 Minutes")
        intent.putExtra("difficulty", "Hard")
        startActivity(intent)
        finish()
    }


    fun getPostitionInArray( item: String):Int{


        when(item){
            "2 Minutes" -> return 0
            "1 Minute" -> return 1
            "30 Seconds" -> return 2
            "15 Seconds" -> return 3
            "Hard" -> return 0
            "Normal" -> return 1
            "Easy" -> return 2

            else -> {
                return 0
            }
        }
    }


}