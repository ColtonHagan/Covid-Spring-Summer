package com.example.dotai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dotai.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = dotOverView(1000)
        controller.dotsLiveData.observe(this,{ updateDots(it) })
    }
    private fun updateDots(dots: ArrayList<dot>) {
        binding.background.update(dots)
    }
}