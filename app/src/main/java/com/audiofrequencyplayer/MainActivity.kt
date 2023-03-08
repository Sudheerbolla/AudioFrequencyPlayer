package com.audiofrequencyplayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.audiofrequencyplayer.databinding.ActivityMainBinding
import com.audiofrequencyplayer.utils.getFrequencyValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            btnPlay.setOnClickListener {
                playTheFrequency()
            }
            audioPlayer = AudioPlayer()
        }
    }

    private fun playTheFrequency() {
        val freq = binding.edtFrequency.text.toString().trim()
        if (!TextUtils.isEmpty(freq) && TextUtils.isDigitsOnly(freq)) {
            audioPlayer.let {
                it.frequency = getFrequencyValue(freq.toInt())
                it.start()
                Handler(Looper.getMainLooper()).postDelayed({
                    it.stop()
                }, 1500)
            }
        } else {
            Toast.makeText(
                this,
                "Please enter a valid frequency between 0 and 24000HZ",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}