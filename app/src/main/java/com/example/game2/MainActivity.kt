package com.example.game2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.game2.Music.SoundPlayer
import com.example.game2.storage.AppPreferences
import com.example.game2.views.MyOpenSqLiteHelper
import com.example.game2.views.TABLE_NAME

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private var tvHighScore: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, SoundPlayer::class.java)
        startService(intent)
        supportActionBar?.hide()
        val btnNewGame = findViewById<Button>(R.id.btn_new_game)
        val btnResetScore = findViewById<Button>(R.id.btn_reset_score)
        val btnExit = findViewById<Button>(R.id.btn_exit)

        startService(intent)

        tvHighScore = findViewById<TextView>(R.id.tv_high_score)

        btnNewGame.setOnClickListener(this::onBtnNewGameClick)
        btnResetScore.setOnClickListener(this::onBtnResetScoreClick)
        btnExit.setOnClickListener(this::onBtnExitClick)

        var highscore = arrayOf("0","0","0","0")
        button_high.setOnClickListener {
            val openSqLiteHelper = MyOpenSqLiteHelper(this,1)
            val db = openSqLiteHelper.writableDatabase
            val cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
            var i=0
            if(cursor.moveToFirst()){
                do {
                    highscore[i++] = cursor.getString(cursor.getColumnIndex("hightScore"))
                }while(cursor.moveToNext())
            }
            Toast.makeText(this,"${highscore[0]} ${highscore[1]} ${highscore[2]} ${highscore[3]}",Toast.LENGTH_LONG).show()
        }


        updateHighScore()
    }

    private fun onBtnNewGameClick(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun onBtnResetScoreClick(view: View) {



        val preferences = AppPreferences(this)
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset", Snackbar.LENGTH_SHORT).show()
        tvHighScore?.text = "High score: ${preferences.getHighScore()}"



    }

    @SuppressLint("SetTextI18n")
    fun updateHighScore() {
        val preferences = AppPreferences(this)
        tvHighScore?.text = "High score: ${preferences.getHighScore()}"
    }

    private fun onBtnExitClick(view: View) {
        System.exit(0)
    }
    override fun onStop() {
        val intent = Intent(this, SoundPlayer::class.java)
        stopService(intent)
        super.onStop()
    }

}