package com.example.comunicacion_aplicaciones

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class FormularioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCiudad: EditText
    private lateinit var etCorreo: EditText
    private lateinit var btnContinuar: Button

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Perfil guardado correctamente", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        etCiudad = findViewById(R.id.etCiudad)
        etCorreo = findViewById(R.id.etCorreo)
        btnContinuar = findViewById(R.id.btnContinuar)

        // Restaurar datos si rota pantalla
        savedInstanceState?.let {
            etNombre.setText(it.getString("nombre"))
            etEdad.setText(it.getString("edad"))
            etCiudad.setText(it.getString("ciudad"))
            etCorreo.setText(it.getString("correo"))
        }

        btnContinuar.setOnClickListener {
            val usuario = Usuario(
                etNombre.text.toString(),
                etEdad.text.toString(),
                etCiudad.text.toString(),
                etCorreo.text.toString()
            )

            val intent = Intent(this, ResumenActivity::class.java)
            intent.putExtra("usuario", usuario)
            launcher.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("nombre", etNombre.text.toString())
        outState.putString("edad", etEdad.text.toString())
        outState.putString("ciudad", etCiudad.text.toString())
        outState.putString("correo", etCorreo.text.toString())
    }
}