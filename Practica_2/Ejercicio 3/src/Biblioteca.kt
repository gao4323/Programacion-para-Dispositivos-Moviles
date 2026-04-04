/*
Clase Biblioteca que implementa la interfaz IBiblioteca.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

class Biblioteca : IBiblioteca {

    // Ahora son públicas (sin private)
    val materialesDisponibles = mutableListOf<Material>()
    val usuarios = mutableMapOf<Usuario, MutableList<Material>>()

    override fun registrarMaterial(material: Material) {
        materialesDisponibles.add(material)
    }

    override fun registrarUsuario(usuario: Usuario) {
        usuarios[usuario] = mutableListOf()
    }

    override fun prestarMaterial(usuario: Usuario, material: Material) {
        if (materialesDisponibles.contains(material)) {
            materialesDisponibles.remove(material)
            usuarios[usuario]?.add(material)
            println("Préstamo realizado")
        } else {
            println("Material no disponible")
        }
    }

    override fun devolverMaterial(usuario: Usuario, material: Material) {
        if (usuarios[usuario]?.contains(material) == true) {
            usuarios[usuario]?.remove(material)
            materialesDisponibles.add(material)
            println("Devolución realizada")
        } else {
            println("El usuario no tiene este material")
        }
    }

    override fun mostrarMaterialesDisponibles() {
        println("Materiales disponibles:")
        materialesDisponibles.forEach { it.mostrarDetalles() }
    }

    override fun mostrarMaterialesPorUsuario(usuario: Usuario) {
        println("Materiales de ${usuario.nombre}:")
        usuarios[usuario]?.forEach { it.mostrarDetalles() }
    }
}