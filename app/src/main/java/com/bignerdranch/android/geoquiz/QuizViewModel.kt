package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {
     val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

     val getCurrentQuestion: Question
        get() = questionBank[currentIndex]


    val currentQuestionAnswer: Boolean
        get() = getCurrentQuestion.answer


    val currentQuestionText: Int
        get() = getCurrentQuestion.textResId


    fun goToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size

//        updateQuestion()
    }
    fun goToPreviousQuestion() {
        currentIndex = (currentIndex - 1)
        if (currentIndex < 0) {
            currentIndex = questionBank.size - 1
        }
        else {
            currentIndex %= questionBank.size - 1
        }



//        updateQuestion()
    }
//    init {
//        Log.d(TAG, "ViewModel instance created")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "ViewModel instance about to be destroyed")
//    }


}