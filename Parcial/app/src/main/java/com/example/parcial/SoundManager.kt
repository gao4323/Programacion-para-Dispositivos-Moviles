package com.example.parcial

import android.content.Context
import android.media.MediaPlayer

class SoundManager(private val context: Context) {

    // Música de fondo
    private var backgroundMusic: MediaPlayer? = null

    // Sonido correcto
    fun playCorrect() {
        MediaPlayer.create(context, R.raw.correcto).start()
    }

    // Sonido incorrecto
    fun playWrong() {
        MediaPlayer.create(context, R.raw.error).start()
    }

    // Iniciar música de fondo
    fun startBackgroundMusic() {

        backgroundMusic = MediaPlayer.create(context, R.raw.musica)

        backgroundMusic?.isLooping = true

        backgroundMusic?.start()
    }

    // Detener música
    fun stopBackgroundMusic() {

        backgroundMusic?.stop()

        backgroundMusic?.release()

        backgroundMusic = null
    }
}