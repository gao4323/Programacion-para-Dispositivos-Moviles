package com.example.prograquiz.data.mock

import com.example.prograquiz.model.*

object MockData {

    // ── Current user ─────────────────────────────────────────────────────────

    val currentUser = User(
        id = "u001",
        username = "GabrielJZ",
        email = "gabriel@lasalle.edu.pe",
        avatarInitials = "GJ",
        bestScore = 95,
        totalGames = 24,
        totalCorrect = 187,
        totalQuestions = 240,
        favoriteLevel = DifficultyLevel.INTERMEDIO
    )

    // ── Questions per level ──────────────────────────────────────────────────

    val basicQuestions = listOf(
        Question(
            id = 1,
            text = "¿Cuál de las siguientes es una estructura de control condicional en programación?",
            options = listOf("for", "if-else", "while", "array"),
            correctIndex = 1,
            explanation = "El if-else es la estructura condicional fundamental. Permite ejecutar diferentes bloques de código según si una condición es verdadera o falsa.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 2,
            text = "¿Qué imprime el siguiente código?\n\nfor (int i = 0; i < 3; i++) {\n   print(i);\n}",
            options = listOf("1 2 3", "0 1 2", "0 1 2 3", "1 2"),
            correctIndex = 1,
            explanation = "El bucle inicia en i=0 y continúa mientras i<3, incrementando i en 1 cada vez. Por lo tanto imprime 0, 1 y 2.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 3,
            text = "¿Cuál es el resultado de: 10 % 3?",
            options = listOf("3", "1", "0", "3.33"),
            correctIndex = 1,
            explanation = "El operador % (módulo) retorna el resto de la división. 10 ÷ 3 = 3 con resto 1, por lo tanto 10 % 3 = 1.",
            level = DifficultyLevel.BASICO
        ),
        Question(
            id = 4,
            text = "¿Qué tipo de dato se usa para almacenar texto en la mayoría de lenguajes?",
            options = listOf("int", "boolean", "String", "float"),
            correctIndex = 2,
            explanation = "El tipo String es el estándar para almacenar cadenas de caracteres (texto) en la mayoría de lenguajes de programación modernos.",
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
            explanation = "El bucle while repite un bloque de código mientras la condición especificada siga siendo verdadera. Se detiene cuando la condición es falsa.",
            level = DifficultyLevel.BASICO
        )
    )

    val intermediateQuestions = listOf(
        Question(
            id = 6,
            text = "¿Qué es la recursividad en programación?",
            options = listOf(
                "Un tipo de bucle for",
                "Una función que se llama a sí misma",
                "Un método de ordenamiento",
                "Una estructura de datos"
            ),
            correctIndex = 1,
            explanation = "La recursividad ocurre cuando una función se llama a sí misma. Debe tener un caso base para evitar la recursión infinita.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 7,
            text = "¿Cuál es la complejidad temporal de buscar un elemento en un arreglo no ordenado?",
            options = listOf("O(1)", "O(log n)", "O(n)", "O(n²)"),
            correctIndex = 2,
            explanation = "En el peor caso hay que revisar todos los elementos del arreglo, lo que resulta en complejidad O(n) — lineal respecto al tamaño del arreglo.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 8,
            text = "¿Qué principio de OOP permite que una clase herede propiedades y métodos de otra?",
            options = listOf("Encapsulamiento", "Polimorfismo", "Abstracción", "Herencia"),
            correctIndex = 3,
            explanation = "La Herencia permite que una clase (hija) adquiera los atributos y métodos de otra clase (padre), promoviendo la reutilización de código.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 9,
            text = "¿Cuál de estas estructuras de datos opera con el principio LIFO (Last In, First Out)?",
            options = listOf("Cola (Queue)", "Pila (Stack)", "Lista enlazada", "Árbol binario"),
            correctIndex = 1,
            explanation = "La Pila (Stack) opera bajo LIFO: el último elemento que entra es el primero en salir. Como una pila de platos.",
            level = DifficultyLevel.INTERMEDIO
        ),
        Question(
            id = 10,
            text = "¿Qué hace el operador && en la mayoría de lenguajes de programación?",
            options = listOf("OR lógico", "NOT lógico", "AND lógico", "XOR lógico"),
            correctIndex = 2,
            explanation = "El operador && representa el AND lógico. Retorna verdadero solo cuando ambas condiciones evaluadas son verdaderas.",
            level = DifficultyLevel.INTERMEDIO
        )
    )

    val advancedQuestions = listOf(
        Question(
            id = 11,
            text = "¿Cuál es la complejidad temporal del algoritmo QuickSort en el caso promedio?",
            options = listOf("O(n)", "O(n log n)", "O(n²)", "O(log n)"),
            correctIndex = 1,
            explanation = "QuickSort tiene complejidad promedio O(n log n). En el peor caso (pivote siempre mínimo/máximo) degrada a O(n²), pero estadísticamente es muy eficiente.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 12,
            text = "¿Qué patrón de diseño asegura que una clase tenga solo una instancia en toda la aplicación?",
            options = listOf("Factory", "Observer", "Singleton", "Strategy"),
            correctIndex = 2,
            explanation = "El patrón Singleton garantiza que una clase tenga exactamente una instancia y proporciona un punto de acceso global a ella.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 13,
            text = "En programación funcional, ¿qué es una función pura?",
            options = listOf(
                "Una función sin parámetros",
                "Una función que siempre retorna el mismo resultado para los mismos inputs y no tiene efectos secundarios",
                "Una función que solo usa tipos primitivos",
                "Una función privada de una clase"
            ),
            correctIndex = 1,
            explanation = "Una función pura siempre produce el mismo output para los mismos inputs y no produce efectos secundarios (no modifica estado externo).",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 14,
            text = "¿Qué es un deadlock en concurrencia?",
            options = listOf(
                "Un error de compilación",
                "Cuando dos o más procesos se bloquean mutuamente esperando recursos que el otro posee",
                "Un tipo de excepción en tiempo de ejecución",
                "Una condición de carrera entre hilos"
            ),
            correctIndex = 1,
            explanation = "Un deadlock ocurre cuando dos o más procesos quedan bloqueados indefinidamente porque cada uno espera que el otro libere un recurso.",
            level = DifficultyLevel.AVANZADO
        ),
        Question(
            id = 15,
            text = "¿Cuál es la diferencia entre BFS y DFS en grafos?",
            options = listOf(
                "BFS usa pila, DFS usa cola",
                "BFS explora por niveles usando cola; DFS explora por profundidad usando pila o recursión",
                "Son equivalentes en tiempo y espacio",
                "BFS solo funciona en grafos dirigidos"
            ),
            correctIndex = 1,
            explanation = "BFS (Breadth-First Search) explora nivel por nivel usando una cola. DFS (Depth-First Search) explora tan profundo como sea posible usando una pila o recursión.",
            level = DifficultyLevel.AVANZADO
        )
    )

    fun getQuestionsByLevel(level: DifficultyLevel): List<Question> = when (level) {
        DifficultyLevel.BASICO -> basicQuestions
        DifficultyLevel.INTERMEDIO -> intermediateQuestions
        DifficultyLevel.AVANZADO -> advancedQuestions
    }

    // ── Ranking ──────────────────────────────────────────────────────────────

    val rankingList = listOf(
        RankingEntry(1, "CodeMaster99", 100, DifficultyLevel.AVANZADO, "CM"),
        RankingEntry(2, "AlgorithmKing", 98, DifficultyLevel.AVANZADO, "AK"),
        RankingEntry(3, "PythonPro", 97, DifficultyLevel.AVANZADO, "PP"),
        RankingEntry(4, "KotlinDev", 95, DifficultyLevel.INTERMEDIO, "KD"),
        RankingEntry(5, "GabrielJZ", 95, DifficultyLevel.INTERMEDIO, "GJ"),
        RankingEntry(6, "JavaGuru", 92, DifficultyLevel.AVANZADO, "JG"),
        RankingEntry(7, "DevLasalle", 88, DifficultyLevel.INTERMEDIO, "DL"),
        RankingEntry(8, "LoopMaster", 85, DifficultyLevel.BASICO, "LM"),
        RankingEntry(9, "RecursiveGirl", 83, DifficultyLevel.INTERMEDIO, "RG"),
        RankingEntry(10, "ByteCoder", 80, DifficultyLevel.BASICO, "BC")
    )

    // ── Game History ─────────────────────────────────────────────────────────

    val gameHistory = listOf(
        GameHistory(1, "23 Jun 2025, 10:45", DifficultyLevel.INTERMEDIO, 95, 5, 5),
        GameHistory(2, "22 Jun 2025, 14:20", DifficultyLevel.BASICO, 100, 5, 5),
        GameHistory(3, "21 Jun 2025, 09:10", DifficultyLevel.AVANZADO, 60, 3, 5),
        GameHistory(4, "20 Jun 2025, 16:55", DifficultyLevel.INTERMEDIO, 80, 4, 5),
        GameHistory(5, "18 Jun 2025, 11:30", DifficultyLevel.AVANZADO, 40, 2, 5),
        GameHistory(6, "15 Jun 2025, 20:05", DifficultyLevel.BASICO, 100, 5, 5),
        GameHistory(7, "13 Jun 2025, 08:40", DifficultyLevel.INTERMEDIO, 60, 3, 5),
        GameHistory(8, "10 Jun 2025, 15:15", DifficultyLevel.BASICO, 80, 4, 5)
    )
}
