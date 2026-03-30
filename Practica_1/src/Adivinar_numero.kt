import kotlin.random.Random

fun main() {
    //Crea el numero aleatorio y pongo un numero de intentos para adivinar
    val numero = Random.nextInt(1, 11)
    var intentos = 5

    //Verifica si adivinamos el nuemro, da pistas sobre el numero y verifica cuando se nos acaben los intentos
    while (intentos > 0) {
        println("Adivina el número del 1-101:")
        val intento = readln().toInt()

        if (intento == numero) {
            println("Adivinaste el número")
            return
        } else if (intento < numero) {
            println("El número es mayor")
        } else {
            println("El número es menor")
        }

        intentos--
        println("Intentos restantes: $intentos")
    }

    //Si no adivinamos el numero
    println("El número era $numero")
}