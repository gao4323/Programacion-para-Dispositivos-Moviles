import 'package:flutter/material.dart';

class PantallaPerfil extends StatelessWidget {
  const PantallaPerfil({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Mi Perfil',
          style: TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontSize: 22,
          ),
        ),
        backgroundColor: const Color(0xFF2E7D32),
        centerTitle: true,
        elevation: 4,
      ),
      backgroundColor: const Color(0xFFF1F8E9),
      body: LayoutBuilder(
        builder: (context, constraints) {
          final bool esAncho = constraints.maxWidth >= 700;

          return SingleChildScrollView(
            child: Center(
              child: ConstrainedBox(
                constraints: const BoxConstraints(maxWidth: 800),
                child: Padding(
                  padding: EdgeInsets.symmetric(
                    horizontal: esAncho ? 60 : 24,
                    vertical: 32,
                  ),
                  child: esAncho
                      ? _layoutHorizontal()
                      : _layoutVertical(),
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  // Layout para pantalla ancha: foto a la izquierda, info a la derecha
  Widget _layoutHorizontal() {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // Columna izquierda: foto + nombre
        SizedBox(
          width: 240,
          child: Column(
            children: [
              _fotoPerfil(),
              const SizedBox(height: 20),
              _nombreYCarrera(),
            ],
          ),
        ),
        const SizedBox(width: 48),
        // Columna derecha: descripción + contacto
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(height: 12),
              _descripcion(),
              const SizedBox(height: 28),
              const Divider(color: Color(0xFFA5D6A7), thickness: 1.2),
              const SizedBox(height: 20),
              _tituloSeccion('Información de contacto'),
              const SizedBox(height: 16),
              _filaContacto(icono: Icons.email_outlined,    texto: 'gabriel.jara@universidad.edu.pe'),
              const SizedBox(height: 12),
              _filaContacto(icono: Icons.phone_outlined,    texto: '+51 987 654 321'),
              const SizedBox(height: 12),
              _filaContacto(icono: Icons.location_on_outlined, texto: 'Arequipa, Perú'),
              const SizedBox(height: 12),
              _filaContacto(icono: Icons.school_outlined,   texto: 'Universidad la Salle'),
            ],
          ),
        ),
      ],
    );
  }

  // Layout para pantalla angosta: todo en columna
  Widget _layoutVertical() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        _fotoPerfil(),
        const SizedBox(height: 20),
        _nombreYCarrera(),
        const SizedBox(height: 16),
        _descripcion(),
        const SizedBox(height: 28),
        const Divider(color: Color(0xFFA5D6A7), thickness: 1.2),
        const SizedBox(height: 20),
        _tituloSeccion('Información de contacto'),
        const SizedBox(height: 16),
        _filaContacto(icono: Icons.email_outlined,       texto: 'gabriel.jara@universidad.edu.pe'),
        const SizedBox(height: 12),
        _filaContacto(icono: Icons.phone_outlined,       texto: '+51 987 654 321'),
        const SizedBox(height: 12),
        _filaContacto(icono: Icons.location_on_outlined, texto: 'Arequipa, Perú'),
        const SizedBox(height: 12),
        _filaContacto(icono: Icons.school_outlined,      texto: 'Universidad la Salle'),
        const SizedBox(height: 30),
      ],
    );
  }

  Widget _fotoPerfil() {
    return Center(
      child: Container(
        width: 150,
        height: 150,
        decoration: const BoxDecoration(
          color: Color(0xFF2E7D32),
          shape: BoxShape.circle,
        ),
        child: const Icon(Icons.person, size: 80, color: Colors.white),
      ),
    );
  }

  Widget _nombreYCarrera() {
    return Column(
      children: [
        const Text(
          'Gabriel Jara',
          style: TextStyle(
            fontSize: 26,
            fontWeight: FontWeight.bold,
            color: Color(0xFF1B5E20),
          ),
          textAlign: TextAlign.center,
        ),
        const SizedBox(height: 10),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          decoration: BoxDecoration(
            color: const Color(0xFF2E7D32),
            borderRadius: BorderRadius.circular(20),
          ),
          child: const Text(
            'Estudiante universitario · Ingeniería de Software',
            style: TextStyle(color: Colors.white, fontSize: 13, fontWeight: FontWeight.w500),
            textAlign: TextAlign.center,
          ),
        ),
      ],
    );
  }

  Widget _descripcion() {
    return const Text(
      'Apasionado por la tecnología y el desarrollo de software. '
          'Me encanta aprender nuevas herramientas y construir soluciones '
          'que mejoren la vida de las personas.',
      style: TextStyle(fontSize: 15, color: Color(0xFF37474F), height: 1.6),
      textAlign: TextAlign.center,
    );
  }

  Widget _tituloSeccion(String titulo) {
    return Text(
      titulo,
      style: const TextStyle(
        fontSize: 17,
        fontWeight: FontWeight.bold,
        color: Color(0xFF1B5E20),
      ),
    );
  }

  Widget _filaContacto({required IconData icono, required String texto}) {
    return Row(
      children: [
        Container(
          width: 44,
          height: 44,
          decoration: BoxDecoration(
            color: const Color(0xFFE8F5E9),
            borderRadius: BorderRadius.circular(10),
            border: Border.all(color: const Color(0xFF81C784), width: 1),
          ),
          child: Icon(icono, color: const Color(0xFF2E7D32), size: 22),
        ),
        const SizedBox(width: 14),
        Expanded(
          child: Text(
            texto,
            style: const TextStyle(fontSize: 14, color: Color(0xFF37474F)),
          ),
        ),
      ],
    );
  }
}