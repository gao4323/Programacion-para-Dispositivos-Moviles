/*
Clase Revista que hereda de Material e incluye información editorial.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

class Revista(
    titulo: String,
    autor: String,
    anioPublicacion: Int,
    val issn: String,
    val volumen: Int,
    val numero: Int,
    val editorial: String
) : Material(titulo, autor, anioPublicacion) {

    override fun mostrarDetalles() {
        println("Revista: $titulo, " +
                "ISSN: $issn, Volumen: $volumen, " +
                "Número: $numero, Editorial: $editorial")
    }
}