package com.programacion.prograquiz.data.mock

import com.programacion.prograquiz.model.DifficultyLevel
import com.programacion.prograquiz.model.GameHistory
import com.programacion.prograquiz.model.Question
import com.programacion.prograquiz.model.RankingEntry
import com.programacion.prograquiz.model.User

object MockData {

    val currentUser = User(
        id             = "u001",
        username       = "Gabriel Jara",
        email          = "gjaraz@ulasalle.edu.pe",
        avatarInitials = "GJ",
        bestScore      = 80,
        totalGames     = 6,
        totalCorrect   = 22,
        totalQuestions = 30,
        favoriteLevel  = DifficultyLevel.INTERMEDIO
    )

    // ── Preguntas ─────────────────────────────────────────────────────────────

    val basicQuestions = listOf(
        Question(
            id = 1,
            text = "¿Cuál es una estructura de control condicional?",
            options = listOf("for", "if-else", "while", "array"),
            correctIndex = 1,
            explanation = "El if-else permite ejecutar código según si una condición es verdadera o falsa.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 2,
            text = "¿Qué imprime este código?\n\nfor (int i = 0; i < 3; i++) {\n   print(i);\n}",
            options = listOf("1 2 3", "0 1 2", "0 1 2 3", "1 2"),
            correctIndex = 1,
            explanation = "El bucle inicia en i=0 y se repite mientras i<3, por eso imprime 0, 1 y 2.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 3,
            text = "¿Cuál es el resultado de: 10 % 3?",
            options = listOf("3", "1", "0", "3.33"),
            correctIndex = 1,
            explanation = "El operador % devuelve el resto de la división. 10 entre 3 da resto 1.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 4,
            text = "¿Qué tipo de dato se usa para almacenar texto?",
            options = listOf("int", "boolean", "String", "float"),
            correctIndex = 2,
            explanation = "El tipo String se usa para almacenar cadenas de texto.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 5,
            text = "¿Cuál es la función de un bucle while?",
            options = listOf(
                "Ejecutar código una sola vez",
                "Ejecutar código mientras una condición sea verdadera",
                "Declarar variables",
                "Llamar a una función"
            ),
            correctIndex = 1,
            explanation = "El while repite un bloque de código mientras la condición sea verdadera.",
            level = DifficultyLevel.BASICO
        )
    )

    val intermediateQuestions = listOf(
        Question(
            id = 6,
            text = "¿Qué es la recursividad?",
            options = listOf(
                "Un tipo de bucle for",
                "Una función que se llama a sí misma",
                "Un método de ordenamiento",
                "Una estructura de datos"
            ),
            correctIndex = 1,
            explanation = "La recursividad ocurre cuando una función se llama a sí misma. Necesita un caso base para detenerse.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 7,
            text = "¿Cuál es la complejidad de buscar en un arreglo no ordenado?",
            options = listOf("O(1)", "O(log n)", "O(n)", "O(n²)"),
            correctIndex = 2,
            explanation = "En el peor caso hay que revisar todos los elementos, resultando en O(n).",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 8,
            text = "¿Qué principio de OOP permite heredar de otra clase?",
            options = listOf("Encapsulamiento", "Polimorfismo", "Abstracción", "Herencia"),
            correctIndex = 3,
            explanation = "La Herencia permite que una clase hija adquiera atributos y métodos de una clase padre.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 9,
            text = "¿Cuál estructura opera con LIFO (Last In, First Out)?",
            options = listOf("Cola (Queue)", "Pila (Stack)", "Lista enlazada", "Árbol"),
            correctIndex = 1,
            explanation = "La Pila (Stack) opera con LIFO: el último en entrar es el primero en salir.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 10,
            text = "¿Qué hace el operador && en programación?",
            options = listOf("OR lógico", "NOT lógico", "AND lógico", "XOR lógico"),
            correctIndex = 2,
            explanation = "El && es el AND lógico. Devuelve verdadero solo si ambas condiciones son verdaderas.",
            level = DifficultyLevel.INTERMEDIO
        )
    )

    val advancedQuestions = listOf(
        Question(
            id = 11,
            text = "¿Complejidad promedio de QuickSort?",
            options = listOf("O(n)", "O(n log n)", "O(n²)", "O(log n)"),
            correctIndex = 1,
            explanation = "QuickSort tiene complejidad promedio O(n log n). En el peor caso degrada a O(n²).",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 12,
            text = "¿Qué patrón asegura una sola instancia de una clase?",
            options = listOf("Factory", "Observer", "Singleton", "Strategy"),
            correctIndex = 2,
            explanation = "El patrón Singleton garantiza exactamente una instancia y un punto de acceso global.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 13,
            text = "¿Qué es una función pura en programación funcional?",
            options = listOf(
                "Una función sin parámetros",
                "Siempre retorna el mismo resultado para los mismos inputs y no tiene efectos secundarios",
                "Una función que solo usa tipos primitivos",
                "Una función privada"
            ),
            correctIndex = 1,
            explanation = "Una función pura siempre produce el mismo output y no produce efectos secundarios.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 14,
            text = "¿Qué es un deadlock?",
            options = listOf(
                "Un error de compilación",
                "Dos procesos bloqueados mutuamente esperando recursos del otro",
                "Una excepción en tiempo de ejecución",
                "Una condición de carrera"
            ),
            correctIndex = 1,
            explanation = "Deadlock: dos procesos quedan bloqueados esperando recursos que el otro posee.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 15,
            text = "¿Diferencia entre BFS y DFS?",
            options = listOf(
                "BFS usa pila, DFS usa cola",
                "BFS recorre por niveles con cola; DFS recorre en profundidad con pila",
                "Son equivalentes",
                "BFS solo funciona en grafos dirigidos"
            ),
            correctIndex = 1,
            explanation = "BFS recorre nivel por nivel (cola). DFS recorre en profundidad (pila o recursión).",
            level = DifficultyLevel.AVANZADO
        )
    )

    fun getQuestionsByLevel(level: DifficultyLevel): List<Question> = when (level) {
        DifficultyLevel.BASICO     -> basicQuestions
        DifficultyLevel.INTERMEDIO -> intermediateQuestions
        DifficultyLevel.AVANZADO   -> advancedQuestions
    }

    // ── Ranking — nombres comunes ─────────────────────────────────────────────

    val rankingList = listOf(
        RankingEntry(1,  "Carlos",   100, DifficultyLevel.AVANZADO,    "CA"),
        RankingEntry(2,  "María",     95, DifficultyLevel.AVANZADO,    "MA"),
        RankingEntry(3,  "José",      90, DifficultyLevel.INTERMEDIO,  "JO"),
        RankingEntry(4,  "Ana",       85, DifficultyLevel.INTERMEDIO,  "AN"),
        RankingEntry(5,  "Luis",      80, DifficultyLevel.INTERMEDIO,  "LU"),
        RankingEntry(6,  "Gabriel",   80, DifficultyLevel.INTERMEDIO,  "GJ"),
        RankingEntry(7,  "Sofía",     75, DifficultyLevel.BASICO,      "SO"),
        RankingEntry(8,  "Diego",     70, DifficultyLevel.BASICO,      "DI"),
        RankingEntry(9,  "Valeria",   65, DifficultyLevel.BASICO,      "VA"),
        RankingEntry(10, "Miguel",    60, DifficultyLevel.BASICO,      "MI")
    )

    // ── Historial — solo 6 partidas recientes ─────────────────────────────────

    val gameHistory = listOf(
        GameHistory(1, "23 Jun 2025, 10:45", DifficultyLevel.INTERMEDIO,  80, 4, 5),
        GameHistory(2, "22 Jun 2025, 14:20", DifficultyLevel.BASICO,     100, 5, 5),
        GameHistory(3, "21 Jun 2025, 09:10", DifficultyLevel.AVANZADO,    60, 3, 5),
        GameHistory(4, "20 Jun 2025, 16:55", DifficultyLevel.INTERMEDIO,  80, 4, 5),
        GameHistory(5, "18 Jun 2025, 11:30", DifficultyLevel.BASICO,     100, 5, 5),
        GameHistory(6, "15 Jun 2025, 20:05", DifficultyLevel.AVANZADO,    40, 2, 5)
    )
}
