/*
Clase principal para probar el sistema de biblioteca.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

fun main() {

    val biblioteca = Biblioteca()

    val usuario1 = Usuario("Gabriel", "Jara", 20)

    val libro1 = Libro(
        "Libertad",
        "Ruby",
        2020,
        "Derecho",
        300
    )

    val revista1 = Revista(
        "Tecnologia",
        "Bernardo",
        2023,
        "1234-5678",
        10,
        2,
        "Ulasalle"
    )

    biblioteca.registrarUsuario(usuario1)
    biblioteca.registrarMaterial(libro1)
    biblioteca.registrarMaterial(revista1)

    println("\n MATERIALES DISPONIBLES ")
    biblioteca.mostrarMaterialesDisponibles()

    println("\n PRÉSTAMO DE LIBRO ")
    biblioteca.prestarMaterial(usuario1, libro1)

    println("\n MATERIALES DEL USUARIO ")
    biblioteca.mostrarMaterialesPorUsuario(usuario1)

    println("\n DEVOLUCIÓN DE LIBRO ")
    biblioteca.devolverMaterial(usuario1, libro1)

    println("\n MATERIALES DISPONIBLES FINAL ")
    biblioteca.mostrarMaterialesDisponibles()
}