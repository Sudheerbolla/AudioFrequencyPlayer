package com.audiofrequencyplayer

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.audiofrequencyplayer.databinding.ActivityMainBinding
import com.audiofrequencyplayer.utils.getFrequencyValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var audioFrequencyPlayer: AudioFrequencyPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            setContentView(root)
            audioFrequencyPlayer = AudioFrequencyPlayer()
            btnPlay.setOnClickListener {
                playTheFrequency()
            }
        }
    }

    /**
     * This method hanldes the click event and basic validation of the user frequency input
     */
    private fun playTheFrequency() {
        val freq = binding.edtFrequency.text.toString().trim()
        if (!TextUtils.isEmpty(freq) && TextUtils.isDigitsOnly(freq)) {
            audioFrequencyPlayer.let {
                it.frequency = getFrequencyValue(freq.toInt())
                it.start()
            }
        } else {
            Toast.makeText(
                this,
                "Please enter a valid frequency between 1 and 24000HZ",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onStop() {
        super.onStop()
        audioFrequencyPlayer.stop()
    }
}