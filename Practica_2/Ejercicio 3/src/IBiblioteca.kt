/*
Interfaz IBiblioteca que define las operaciones básicas de una biblioteca.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

interface IBiblioteca {
    fun registrarMaterial(material: Material)
    fun registrarUsuario(usuario: Usuario)
    fun prestarMaterial(usuario: Usuario, material: Material)
    fun devolverMaterial(usuario: Usuario, material: Material)
    fun mostrarMaterialesDisponibles()
    fun mostrarMaterialesPorUsuario(usuario: Usuario)
}