/*
Clase Producto con precio y descuento, incluye validaciones y cálculo del precio final.
Autor: Gabriel Matias Jara Zapana
Fecha creación: 04/04/2026
Fecha última modificación: 04/04/2026
*/

class Producto {

    //Variable precio que verifica que el precio no sea menor que 0
    var precio:Double = 0.0
        set(valor) {
            if (valor >= 0) {
                field = valor
            } else {
                println("El precio no puede ser menor que 0")
            }
        }
//Variable descuento que verifica que el descuento este entre 0 y 100
    var descuento: Double = 0.0
        set(valor) {
            if (valor >= 0 && valor <= 100) {
                field = valor
            } else {
                println("El descuento debe estar entre 0 y 100")
            }
        }
//Funcion que calcula el precio final despues de aplicar el descuento
    fun PrecioF(): Double {
        return precio-(precio*(descuento/100))
    }
//Funcion para imprimir el precio, descuento y  el precio final
    fun imprimir(){
        println("PRECIO DEL PRODUCTO: $precio")
        println("DESCUENTO: $descuento")
        println("PRECIO FINAL: ${PrecioF()}")
    }
}

fun main() {

    val producto = Producto()
    producto.precio = 150.0
    producto.descuento = 20.0
    producto.imprimir()

}