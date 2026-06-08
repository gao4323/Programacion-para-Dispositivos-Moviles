import 'package:flutter/material.dart';
import 'pantalla_inicio.dart';
import 'pantalla_hobbies.dart';
import 'pantalla_perfil.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Mi App Personal',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFF1A73E8)),
        useMaterial3: true,
      ),
      home: const PantallaInicio(), //Pantalla de Inicio
      //home: const PantallaPerfil(), //Pantalla de Perfil
      //home: const PantallaHobbies(), //Pantalla de Hobbies
    );
  }
}