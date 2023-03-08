package com.audiofrequencyplayer

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.audiofrequencyplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)

            btnPlay.setOnClickListener {
                playTheFrequency()
            }
            initComponents()
        }

    }

    /**
     * For all initializations and listeners
     */
    private fun initComponents() {

    }

    fun playTheFrequency() {
        val freq = binding.edtFrequency.text.toString().trim()
        if (!TextUtils.isEmpty(freq) && TextUtils.isDigitsOnly(freq)) {

        } else {
            Toast.makeText(
                this,
                "Please enter a valid frequency between 0 and 24000HZ",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}