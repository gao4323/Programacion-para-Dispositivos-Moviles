package com.example.parcial

import android.content.Context
import android.media.MediaPlayer

// Clase que administra los sonidos del juego
class SoundManager(private val context: Context) {

    private var backgroundMusic: MediaPlayer? = null

    //Sonido de acierto
    fun playCorrect() {
        MediaPlayer.create(context, R.raw.correcto).start()
    }

    //Sonido de fallo
    fun playWrong() {
        MediaPlayer.create(context, R.raw.error).start()
    }

    //Musica de fondo
    fun startBackgroundMusic() {

        // Evita crear múltiples reproductores si ya existe uno
        if (backgroundMusic == null) {
            backgroundMusic = MediaPlayer.create(context, R.raw.musica)
            backgroundMusic?.isLooping = true
            backgroundMusic?.start()
        }
    }

    // Detener la música de fondo
    fun stopBackgroundMusic() {

        backgroundMusic?.apply {

            // Verifica si la música se está reproduciendo y la detiene
            if (isPlaying) {
                stop()
            }
            release()
        }

        backgroundMusic = null
    }
}