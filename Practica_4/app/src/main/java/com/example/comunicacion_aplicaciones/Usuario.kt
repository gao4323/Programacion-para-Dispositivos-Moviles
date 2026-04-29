package com.example.comunicacion_aplicaciones

import java.io.Serializable

data class Usuario(
    val nombre: String,
    val edad: String,
    val ciudad: String,
    val correo: String
) : Serializable