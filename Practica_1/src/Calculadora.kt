fun main() {
    while (true) {
        //Muestra las opciones de la calculadora
        println("1. Suma")
        println("2. Resta")
        println("3. Multiplicación")
        println("4. División")
        println("5. Salir")

        //guarda la opcion que ingresaste
        val opcion = readln().toInt()


        if (opcion == 5) {
            println("Saliendo...")
            break
        }

        //pide 2 numeros para realizar la operacion
        println("Ingrese primer número:")
        val a = readln().toDouble()

        println("Ingrese segundo número:")
        val b = readln().toDouble()

        //Muestra el resultado de la operacion que elejiste
        when (opcion) {
            1 -> println("Resultado: ${a + b}")
            2 -> println("Resultado: ${a - b}")
            3 -> println("Resultado: ${a * b}")
            4 -> {
                if (b != 0.0)
                    println("Resultado: ${a / b}")
                else
                    println("No se puede dividir entre 0")
            }
            else -> println("Opción inválida")
        }
    }
}