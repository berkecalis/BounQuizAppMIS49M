package com.example.bounquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.bounquizapp.databinding.ActivityFinalBinding

class FinalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalBinding
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val totalScore = intent.getIntExtra("CorrectAnswerCount", 0)

        if(totalScore>=6){
            imageView=findViewById(R.id.gidLabel)
            imageView.setImageResource(R.drawable.leogif)
        }else{
            imageView=findViewById(R.id.gidLabel)
            imageView.setImageResource(R.drawable.failgif)
        }

        binding.finishingLabel.text=getString(R.string.scoringLabel, totalScore)
        binding.reSolveButton.setOnClickListener {
            startActivity(Intent(this@FinalActivity, MainActivity::class.java))
        }
    }
    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Can't go back!", Toast.LENGTH_SHORT).show()
    }
}