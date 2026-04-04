/*
Clase Libro que hereda de Material e incluye género y número de páginas.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

class Libro(
    titulo: String,
    autor: String,
    anioPublicacion: Int,
    val genero: String,
    val numeroPaginas: Int
) : Material(titulo, autor, anioPublicacion) {

    override fun mostrarDetalles() {
        println("Libro: $titulo, Autor: $autor, " +
                "Año: $anioPublicacion, Género: $genero, " +
                "Páginas: $numeroPaginas")
    }
}