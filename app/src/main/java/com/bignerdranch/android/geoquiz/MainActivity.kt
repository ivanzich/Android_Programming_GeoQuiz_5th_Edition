package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.R.id.next_button
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import kotlin.math.floor

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener { view: View ->
            Log.d("Ivan", "On True clicked")
            quizViewModel.getCurrentQuestion.userAnswer = true
            checkAnswer(true)
            disableButtons()
            val isComplete = checkIfQuizCompleted()
            if (isComplete) {
                showCompletedToast()
            }
        }

        binding.falseButton.setOnClickListener { view: View ->
            Log.d("Ivan", "On False clicked")
            quizViewModel.getCurrentQuestion.userAnswer = false
            checkAnswer(false)
            disableButtons()
            val isComplete = checkIfQuizCompleted()
            if (isComplete) {
                showCompletedToast()
            }
        }

        binding.nextButton.setOnClickListener {
           quizViewModel.goToNextQuestion()
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.goToPreviousQuestion()
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.goToNextQuestion()
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }


    private  fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        val question = quizViewModel.getCurrentQuestion
        if (question.hasAnswered) {
            disableButtons()
        } else {
            enableButtons()
        }
    }

    private fun disableButtons() {
        binding.trueButton.isEnabled = false
        binding.trueButton.alpha = 0.5f
        binding.falseButton.isEnabled = false
        binding.falseButton.alpha = 0.5f
    }

    private fun enableButtons() {
        binding.trueButton.isEnabled = true
        binding.trueButton.alpha = 1.0f
        binding.falseButton.isEnabled = true
        binding.falseButton.alpha = 1.0f
    }

    private fun checkIfQuizCompleted(): Boolean {
        if (quizViewModel.questionBank.all { it.hasAnswered }) {
            return true
        }
        return false
    }

    private fun showCompletedToast() {
        val numCorrect = quizViewModel.questionBank.filter { it.answer == it.userAnswer  }.size.toDouble()
        val percentage = (numCorrect.div(quizViewModel.questionBank.size)).times(100)
        Toast.makeText(this, "Score: ${floor(percentage).toInt()}%", Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        quizViewModel.getCurrentQuestion.hasAnswered = true
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}