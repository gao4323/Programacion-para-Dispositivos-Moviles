fun main() {

    //Pide que ingreses el salario mensual del empleado
    println("Ingrese su salario:")
    val salario = readln().toDouble()

    //Pide la calificacion de rendimiento del empleado
    println("Ingrese su puntuación del 1 al 10:")
    val puntos = readln().toInt()

    //Rendimiento del empleado
    val nivel = when (puntos) {
        in 0..3 -> "Inaceptable"
        in 4..6 -> "Aceptable"
        in 7..10 -> "Meritorio"
        else -> "Puntuación inválida"
    }

    //Calcula la cantidad de dinero que recibira
    val dinero = salario * (puntos / 10.0)

    //Imprime el nivel y el dinero recibido
    println("Nivel: $nivel")
    println("Dinero recibido: $dinero")
}