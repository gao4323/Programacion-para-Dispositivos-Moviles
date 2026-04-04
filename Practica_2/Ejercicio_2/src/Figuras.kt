/*
Creacion de las clases figuras con herencia de la clase abstracta
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

//Clase Cuadrado con el calculo de su area y perimetro
class Cuadrado(var lado: Double): Shape() {

    override fun Area(): Double {
        return lado * lado
    }

    override fun Perimetro(): Double {
        return 4 * lado
    }
}

//Clase Rectangulo con el calculo de su area y perimetro
class Rectangulo(var base: Double, var altura: Double) : Shape() {

    override fun Area(): Double {
        return base * altura
    }

    override fun Perimetro(): Double {
        return 2 * (base + altura)
    }

}

//Clase Circulo con el calculo de su area y perimetro
class Circulo(var radio: Double) : Shape() {

    override fun Area(): Double {
        return Math.PI * radio * radio
    }

    override fun Perimetro(): Double {
        return 2 * Math.PI * radio
    }

}

fun main() {
    val cuadrado = Cuadrado(4.0)
    val rectangulo = Rectangulo(5.0, 3.0)
    val circulo = Circulo(2.5)

    println("Cuadrado:")
    cuadrado.imprimir()

    println("\nRectángulo:")
    rectangulo.imprimir()

    println("\nCírculo:")
    circulo.imprimir()
}