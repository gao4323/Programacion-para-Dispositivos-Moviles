/*
 * Descripción: Actividad principal de la aplicación Gestor de Contactos.
 *              Configura el NavController y el NavHost para la navegación
 *              entre ListaScreen y FormularioScreen. El estado de la lista
 *              de contactos se maneja globalmente con remember en este nivel
 *              para permitir la comunicación entre pantallas.
 * Autor: Gabriel Matias Jara Zapana
 * Fecha de creación: 2025-05-05
 * Fecha de última modificación: 2025-05-05
 */
package com.example.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegacion.ui.theme.NavegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val contactosState = remember {
                        mutableStateOf(
                            listOf(
                                Contacto(id = 1, nombre = "Ana García",    telefono = "987-654-321", favorito = true),
                                Contacto(id = 2, nombre = "Carlos López",  telefono = "912-345-678", favorito = false),
                                Contacto(id = 3, nombre = "María Torres",  telefono = "956-789-012", favorito = true),
                                Contacto(id = 4, nombre = "Luis Ramírez",  telefono = "934-567-890", favorito = false),
                                Contacto(id = 5, nombre = "Sofía Mendoza", telefono = "978-234-567", favorito = false)
                            )
                        )
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "lista"
                    ) {
                        composable(route = "lista") {
                            ListaScreen(
                                navController = navController,
                                contactosState = contactosState
                            )
                        }
                        composable(route = "formulario") {
                            FormularioScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}