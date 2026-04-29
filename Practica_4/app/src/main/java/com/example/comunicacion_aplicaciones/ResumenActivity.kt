package com.example.comunicacion_aplicaciones
import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ResumenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)

        val tvResumen = findViewById<TextView>(R.id.tvResumen)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        val usuario = intent.getSerializableExtra("usuario") as Usuario

        tvResumen.text = """
            Nombre: ${usuario.nombre}
            Edad: ${usuario.edad}
            Ciudad: ${usuario.ciudad}
            Correo: ${usuario.correo}
        """.trimIndent()

        btnConfirmar.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        btnVolver.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}