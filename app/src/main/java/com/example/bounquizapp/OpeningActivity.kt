package com.example.bounquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import com.example.bounquizapp.databinding.ActivityOpeningBinding

class OpeningActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOpeningBinding
    var runnable : Runnable = Runnable{}
    var handler = Handler()
    var numara2 = 16

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        runnable = object:Runnable{
            override fun run() {
                numara2--
                binding.openingCounter.text = "Quiz will start in ${numara2} seconds"
                handler.postDelayed(runnable,1000)
                if(numara2==0){
                    handler.removeCallbacks(runnable)
                    val intent = Intent(this@OpeningActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        handler.post(runnable)
        binding.openQuiz.setOnClickListener {
            startActivity(Intent(this@OpeningActivity, MainActivity::class.java))
            handler.removeCallbacks(runnable)
        }
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()
    }
}