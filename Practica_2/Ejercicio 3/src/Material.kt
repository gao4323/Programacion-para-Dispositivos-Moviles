/*
Clase abstracta Material para definir los datos básicos de un material de biblioteca.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

abstract class Material(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int
) {
    abstract fun mostrarDetalles()
}