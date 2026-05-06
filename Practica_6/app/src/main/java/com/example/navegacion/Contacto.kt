/*
 * Descripción: Data class que representa un contacto en la aplicación Gestor de Contactos.
 *              Contiene nombre, teléfono y estado de favorito.
 * Autor: Práctica 6 - Navegación y Listas en Jetpack Compose
 * Fecha de creación: 2025-05-05
 * Fecha de última modificación: 2025-05-05
 */

package com.example.navegacion


data class Contacto(
    val id: Int,
    val nombre: String,
    val telefono: String,
    val favorito: Boolean = false
)
