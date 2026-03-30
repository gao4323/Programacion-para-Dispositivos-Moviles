import kotlin.random.Random

fun main() {
    //Oponente con eleccion aleatoria
    val opciones = listOf("piedra", "papel", "tijera")
    val oponente = opciones[Random.nextInt(3)]

    //Pide que elijas una opcion
    println("Elige piedra, papel o tijera:")
    val usuario = readln().lowercase()

    //Muestra que es lo que elijio el oponente
    println("Computadora eligió: $oponente")

    //Define y muestra si ganaste, perdiste o empataste
    if (usuario == oponente) {
        println("Empate")
    } else if (
        (usuario == "piedra" && oponente == "tijera") ||
        (usuario == "papel" && oponente == "piedra") ||
        (usuario == "tijera" && oponente == "papel")
    ) {
        println("Ganaste")
    } else {
        println("Perdiste")
    }
}