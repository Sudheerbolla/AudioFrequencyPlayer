package com.audiofrequencyplayer

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import com.audiofrequencyplayer.utils.SOUND_DURATION
import kotlin.math.roundToLong
import kotlin.math.sin

class AudioFrequencyPlayer : Runnable {

    var frequency = 0
    protected var level = 16384.0
    protected var thread: Thread? = null
    private var audioTrack: AudioTrack? = null

    fun start() {
        thread = Thread(this, "AudioPlayer")
        thread!!.start()
//      Using this to prevent the sound overlapping from noise completely. Its temporary fix
        setVolume(0f)
        Thread.sleep(750)
        setVolume(1f)
//      Using this because we need to stop the sound after specific time. or play for that duration
        Handler(Looper.getMainLooper()).postDelayed({
            stop()
        }, SOUND_DURATION)
    }

    fun setVolume(vol: Float) {
        audioTrack?.setVolume(vol)
    }

//        fun startForTime(longMS: Long) {
//            thread = Thread(this, "AudioPlayer")
//            thread!!.start()
////            longMS
//        }

    fun stop() {
        val t = thread
        thread = null
        try {
            // Wait for the thread to exit
            if (t != null && t.isAlive) t.join()
        } catch (e: Exception) {
        }
    }

    override fun run() {
        processAudio()
    }

    // Process audio
    private fun processAudio() {
        val buffer: ShortArray
        val rate = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC)
        val bufferSize = 512
        val sineWave = 2.0 * Math.PI / rate
//creating audio track to play a sound with our calculated buffer using frequency.
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            ).setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(rate)
                    //                AudioFormat.CHANNEL_OUT_STEREO,
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            ).setBufferSizeInBytes(bufferSize)
            .build()

        val state = audioTrack!!.state
        if (state != AudioTrack.STATE_INITIALIZED) {
            audioTrack!!.release()
            return
        }
//        audioTrack!!.play()

        buffer = ShortArray(bufferSize)

        //        We are using sine wave only here.
        var f = frequency.toDouble()
        var l = 0.0
        var q = 0.0
        while (thread != null) {
            audioTrack!!.play()
            for (i in buffer.indices) {
                f += (frequency - f) / 4096.0
                l += (level * 16384.0 - l) / 4096.0
                q += if (q + f * sineWave < Math.PI) f * sineWave else f * sineWave - 2.0 * Math.PI
                buffer[i] = (sin(q) * l).roundToLong().toShort()
            }
            audioTrack!!.write(buffer, 0, buffer.size)
        }
        audioTrack!!.stop()
        audioTrack!!.release()
    }
}