import 'package:flutter/material.dart';

class PantallaHobbies extends StatelessWidget {
  const PantallaHobbies({super.key});

  final List<Map<String, dynamic>> hobbies = const [
    {
      'icono': Icons.code_rounded,
      'titulo': 'Programación',
      'descripcion': 'Me encanta crear aplicaciones y resolver problemas con código. C++ y Java son mis favoritos.',
      'color': Color(0xFF1565C0),
      'colorFondo': Color(0xFFE3F2FD),
      'imagenUrl': 'https://concepto.de/wp-content/uploads/2020/08/Programacion-informatica-scaled-e1724960033513.jpg',
    },
    {
      'icono': Icons.sports_esports_rounded,
      'titulo': 'Videojuegos',
      'descripcion': 'Me gustan mucho los juegos de peleas, en especial Dragon Ball FighterZ. También los juegos roguelike como Mewgenics o The Binding of Isaac.',
      'color': Color(0xFF00695C),
      'colorFondo': Color(0xFFE0F2F1),
      'imagenUrl': 'https://upload.wikimedia.org/wikipedia/en/a/ad/DBFZ_cover_art.jpg',
    },
    {
      'icono': Icons.sports_handball_rounded,
      'titulo': 'Deporte',
      'descripcion': 'Juego handball para el club Handball Arequipa. Fui seleccionado regional y nacional.',
      'color': Color(0xFF2E7D32),
      'colorFondo': Color(0xFFE8F5E9),
      'imagenUrl': 'https://upload.wikimedia.org/wikipedia/commons/f/f5/Handball_07.jpg',
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Mis Hobbies',
          style: TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.bold,
            fontSize: 22,
          ),
        ),
        backgroundColor: const Color(0xFFE65100),
        centerTitle: true,
        elevation: 4,
      ),
      backgroundColor: const Color(0xFFFFF8F1),
      body: LayoutBuilder(
        builder: (context, constraints) {
          int columnas = 1;
          if (constraints.maxWidth >= 900) {
            columnas = 3;
          } else if (constraints.maxWidth >= 600) {
            columnas = 2;
          }

          return SingleChildScrollView(
            child: Padding(
              padding: EdgeInsets.symmetric(
                horizontal: constraints.maxWidth >= 900 ? 60 : 20,
                vertical: 24,
              ),
              child: Column(
                children: [
                  const Text(
                    'Cosas que me apasionan',
                    style: TextStyle(
                      fontSize: 18,
                      color: Color(0xFF795548),
                      fontStyle: FontStyle.italic,
                    ),
                  ),
                  const SizedBox(height: 28),
                  GridView.builder(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: columnas,
                      crossAxisSpacing: 20,
                      mainAxisSpacing: 20,
                      childAspectRatio: columnas == 1 ? 1.4 : 0.85,
                    ),
                    itemCount: hobbies.length,
                    itemBuilder: (context, index) {
                      final hobby = hobbies[index];
                      return _tarjetaHobby(
                        icono: hobby['icono'] as IconData,
                        titulo: hobby['titulo'] as String,
                        descripcion: hobby['descripcion'] as String,
                        color: hobby['color'] as Color,
                        colorFondo: hobby['colorFondo'] as Color,
                        imagenUrl: hobby['imagenUrl'] as String,
                      );
                    },
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Widget _tarjetaHobby({
    required IconData icono,
    required String titulo,
    required String descripcion,
    required Color color,
    required Color colorFondo,
    required String imagenUrl,
  }) {
    return Container(
      decoration: BoxDecoration(
        color: colorFondo,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: color.withOpacity(0.25), width: 1.2),
        boxShadow: [
          BoxShadow(
            color: color.withOpacity(0.10),
            blurRadius: 12,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Imagen — ocupa 55% de la tarjeta
          Expanded(
            flex: 55,
            child: ClipRRect(
              borderRadius: const BorderRadius.vertical(top: Radius.circular(16)),
              child: Image.network(
                imagenUrl,
                width: double.infinity,
                fit: BoxFit.cover,
                loadingBuilder: (context, child, loadingProgress) {
                  if (loadingProgress == null) return child;
                  return Container(
                    color: color.withOpacity(0.08),
                    child: Center(
                      child: CircularProgressIndicator(color: color),
                    ),
                  );
                },
                errorBuilder: (context, error, stackTrace) => Container(
                  color: color.withOpacity(0.12),
                  child: Center(
                    child: Icon(icono, size: 50, color: color),
                  ),
                ),
              ),
            ),
          ),

          // Contenido — ocupa 45% de la tarjeta
          Expanded(
            flex: 45,
            child: Padding(
              padding: const EdgeInsets.all(14.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Container(
                        width: 34,
                        height: 34,
                        decoration: BoxDecoration(
                          color: color,
                          borderRadius: BorderRadius.circular(9),
                        ),
                        child: Icon(icono, color: Colors.white, size: 20),
                      ),
                      const SizedBox(width: 10),
                      Text(
                        titulo,
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: color,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  Text(
                    descripcion,
                    style: const TextStyle(
                      fontSize: 13,
                      color: Color(0xFF546E7A),
                      height: 1.5,
                    ),
                    maxLines: 4,
                    overflow: TextOverflow.ellipsis,
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}