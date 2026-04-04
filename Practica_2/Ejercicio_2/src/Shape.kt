/*
Clase abstracta para poder calcular el area y el perimetro
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
 */

abstract class Shape(){
    //Funciones para calcualr el area y Perimetro
    abstract fun Area(): Double
    abstract fun Perimetro(): Double

    //Imprime los resultados
    fun imprimir() {
        println("Área: ${Area()}")
        println("Perímetro: ${Perimetro()}")
    }
}
