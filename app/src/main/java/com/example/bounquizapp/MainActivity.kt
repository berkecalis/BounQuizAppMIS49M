package com.example.bounquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bounquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var correctAnswer: String? = null
    private var wrongAnswer1: String? = null
    private var wrongAnswer2: String? = null
    private var wrongAnswer3: String? = null
    private var correctAnswerCounter = 0
    private var questionCounter = 1
    private val totalQuestionNumber = 10
    private var questionTrueCounter = 0
    private var jokerRightCounter = 1

    var numara=100
    var runnable : Runnable = Runnable{}
    var handler =Handler()

    private val questionData = arrayListOf(
        arrayListOf<String>("Boğaziçi kaç senesinde kurulmuştur?", "1863", "1923", "1843", "1883"),
        arrayListOf<String>("MIS 49M kodlu dersi hangi hoca vermektedir?", "Atıl", "Ali", "Ceylan", "Hande"),
        arrayListOf<String>("Boğaziçi Üniversitesi metro hattının kodu nedir?", "M6", "M4", "M2", "M8"),
        arrayListOf<String>("Aşağıdakilerden hangisi Kuzey Kampüsü'nde bulunmamaktadır?", "Natuk Birkan", "Kare Blok", "Abdullah Kuran Kütüphanesi", "New Hall"),
        arrayListOf<String>("Aşağıdaki bölümlerden hangisi Boğaziçi'nde okutulmamaktadır?", "Mimarlık", "İşletme", "MIS", "Sosyoloji"),
        arrayListOf<String>("Şehir dışından gelip yatılı hazırlık okuyacak öğrenci hangi kampüste kalacaktır?", "Kilyos", "Kuzey", "Güney", "Hisar"),
        arrayListOf<String>("Aşağıdaki sokaklardan hangisi öğrenciler arasında popülerdir?", "Cami sokak", "Kilise sokak", "Nurdoğan sokak", "İmkansız sokak"),
        arrayListOf<String>("Boğaziçi Üniversitesi aşağıdaki bankalardan hangisiyle çalışmaktadır?", "Garanti BBVA", "Burgan Bank", "HSBC", "Yapı ve Kredi"),
        arrayListOf<String>("Boğaziçi'nin en ünlü kampüsünde aşağıdaki hayvanlardan hangisi bolca bulunur?", "Kedi", "Tavşan", "Kertenkele", "Orangutan"),
        arrayListOf<String>("'Boğaziçi Çim' hangi kampüste bulunmaktadır?", "Güney", "Kuzey", "Batı", "Doğu")
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        questionData.shuffle()
        nextQuestion()
        geriSayim(view)
        /*object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timerCount.text = "You have ${millisUntilFinished / 1000} seconds"

            }

            override fun onFinish() {
                    Toast.makeText(this@MainActivity, "Time is up!", Toast.LENGTH_LONG).show()
                    binding.timerCount.text = "You have 0 seconds"
                    val intent = Intent(this@MainActivity, FinalActivity::class.java)
                    intent.putExtra("CorrectAnswerCount", correctAnswerCounter)
                    startActivity(intent)
            }
        }.start()*/
    }
    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Can't go back!", Toast.LENGTH_SHORT).show()
    }
    fun geriSayim(view: View){
        runnable = object:Runnable{
            override fun run() {
                numara--
                binding.timerCount.text = "You have ${numara} seconds"
                handler.postDelayed(runnable,1000)
                if(numara==0){
                    geriSayimDurdur(view)
                }
            }
        }
        handler.post(runnable)
    }
    fun geriSayimDurdur(view: View){
        handler.removeCallbacks(runnable)
        Toast.makeText(this@MainActivity, "Time is up!", Toast.LENGTH_LONG).show()
        val intent = Intent(this@MainActivity, FinalActivity::class.java)
        intent.putExtra("CorrectAnswerCount", correctAnswerCounter)
        startActivity(intent)
    }
    fun nextQuestion() {
        binding.questionCounter.text = getString(R.string.counterLabel, questionCounter)
        binding.trueAnswerQuantityId.text=getString(R.string.trueAnswerQuantity,questionTrueCounter)
        val quiz = questionData.get(0)
        binding.relatedQuestion.text = quiz[0]
        correctAnswer = quiz[1]
        wrongAnswer1 = quiz[2]
        wrongAnswer2 = quiz[3]
        wrongAnswer3 = quiz[4]
        quiz.removeAt(0)
        quiz.shuffle()
        binding.option1.text = quiz[0]
        binding.option2.text = quiz[1]
        binding.option3.text = quiz[2]
        binding.option4.text = quiz[3]
        questionData.removeAt(0)
        binding.option1.setEnabled(true)
        binding.option2.setEnabled(true)
        binding.option3.setEnabled(true)
        binding.option4.setEnabled(true)
    }
    fun jokerRightPressed(view: View){
        Toast.makeText(this@MainActivity, "You have used your only joker right!", Toast.LENGTH_LONG).show()
        // binding.jokerBtton.setBackgroundResource(R.color.purple_700)
        binding.jokerBtton.visibility= View.GONE
        //binding.jokerBtton.setEnabled(false)
        var x =binding.option1.text
        var y =binding.option2.text
        var z =binding.option3.text
        var t =binding.option4.text
        var deletedoption=2
        if((x==wrongAnswer1 || x==wrongAnswer2 || x==wrongAnswer3)&&deletedoption!=0) {
            binding.option1.setEnabled(false)
            deletedoption--
        }
        if((y==wrongAnswer1 || y==wrongAnswer2 || y==wrongAnswer3)&&deletedoption!=0) {
            binding.option2.setEnabled(false)
            deletedoption--
        }
        if((z==wrongAnswer1 || z==wrongAnswer2 || z==wrongAnswer3)&&deletedoption!=0) {
            binding.option3.setEnabled(false)
            deletedoption--
        }
        if((t==wrongAnswer1 || t==wrongAnswer2 || t==wrongAnswer3)&&deletedoption!=0) {
            binding.option4.setEnabled(false)
            deletedoption--
        }
    }
    fun checkTrueAnswer(view: View) {
        val replyBtton: Button = findViewById(view.id)
        val textOfBtton = replyBtton.text.toString()
        val alertTitle: String
        if (textOfBtton == correctAnswer){
            alertTitle = "It's correct answer, congrats"
            correctAnswerCounter++
            questionTrueCounter++
        }else{
            alertTitle = "Wrong decision"
        }

        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("True answer was $correctAnswer")
            .setPositiveButton("Alright") {dialogInterface, i->
                howManyQuestion()
            }
            .setCancelable(false)
            .show()
    }
        fun howManyQuestion() {
            if(questionCounter==totalQuestionNumber){
                //numara=100000000
                handler.removeCallbacks(runnable)
                val intent = Intent(this@MainActivity, FinalActivity::class.java)
                intent.putExtra("CorrectAnswerCount", correctAnswerCounter)
                startActivity(intent)
            }else{
                questionCounter++
                nextQuestion()
            }
        }
}