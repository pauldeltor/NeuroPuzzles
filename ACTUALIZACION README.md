# NeuroPuzzles 🧠🎮  
**Aplicación Android de gamificación terapéutica (Memoria / Atención)**  
**Estado:** Prototipo funcional (Alpha) — **Versión:** v0.5 (Avance Módulo 5)

NeuroPuzzles es una aplicación móvil **Android** orientada a la **gamificación terapéutica**. Implementa un juego tipo *“voltear cartas y encontrar pares”* para estimular procesos **atencionales** y **mnésicos** en niños de **6 a 11 años**, especialmente en contextos clínicos vinculados a **TDAH**.

En esta versión, la app integra consumo de imágenes en tiempo real desde la **Dragon Ball API**, y cuenta con una **Interfaz de Administrador** protegida para que el terapeuta configure la dificultad (tamaño del tablero) según las necesidades del niño.

---

## 1. Características principales (v0.5)
- Juego de memoria por pares con sesiones breves y retroalimentación inmediata.
- Tablero dinámico por dificultad:
  - **Fácil:** 4×4 (16 cartas)  
  - **Medio:** 6×6 (36 cartas)  
  - **Difícil:** 8×8 (64 cartas)
- **Consumo de API** pública (Dragon Ball) para obtener personajes e imágenes.
- **Carga y caché de imágenes** con Glide.
- **Persistencia básica** con `SharedPreferences` (configuración de dificultad y último puntaje).
- **Panel de Administrador** con acceso por PIN y selector de dificultad.

> **PIN Admin (hardcoded, v0.5):** `1234`

---

## 2. Capturas (Módulo 5)
En el documento de avance se muestran tres pantallas: **Menú principal**, **Panel de administrador**, y **Juego en progreso**.  
(En el repositorio puedes incluirlas en `docs/screenshots/` y referenciarlas aquí.)

Ejemplo de estructura sugerida:
```
docs/
  screenshots/
    01_menu_principal.png
    02_admin_panel.png
    03_juego_progreso.png
```

Y en este README:
```md
![Menú principal](docs/screenshots/01_menu_principal.png)
![Panel admin](docs/screenshots/02_admin_panel.png)
![Juego en progreso](docs/screenshots/03_juego_progreso.png)
```

---

## 3. Changelog (Registro de cambios)
### [v0.1] — Módulos 1–4 (Concepto y Diseño)
- Definición de problemática y alcance terapéutico.
- Wireframes textuales y flujo de navegación.
- Selección de herramientas (**Kotlin**, **Android Studio**).
- Definición de requisitos funcionales y no funcionales.

### [v0.5] — Módulo 5 (Versión actual — Implementación técnica)
- Implementación de arquitectura base en Android Studio con Kotlin.
- Integración de **Retrofit** para consumo de API pública.
- Implementación de **Glide** para carga y caché de imágenes remotas.
- Lógica de juego terminada: barajado, lógica de pares, conteo de intentos.
- Persistencia: `SharedPreferences` (dificultad y último puntaje).
- Interfaz de Administrador con PIN y selector de dificultad.
- Ajuste de URL base para resolver problemas de conexión SSL/seguridad.

### [v1.0] — Módulos 7–8 (Planificación futura — Entrega final)
- Implementación de **Room Database** para historial de sesiones persistente y detallado.
- Mejoras UI/UX: animaciones al voltear cartas y sonidos de retroalimentación.
- Refinamiento del manejo de errores (pantallas de *“sin conexión”*).
- Pruebas finales y limpieza de código.

---

## 4. Stack tecnológico
- **Plataforma objetivo:** Android (teléfonos y tablets)
- **IDE:** Android Studio
- **Lenguaje:** Kotlin
- **UI:** XML (Views)
- **Arquitectura:** MVC / MVVM simplificado (separación por capas: UI, Network, Model)
- **Networking:** Retrofit + Gson Converter
- **Imágenes:** Glide
- **Persistencia:** SharedPreferences
- **Control de versiones:** Git + GitHub

---

## 5. Funcionalidad (Resumen de requisitos)
| ID | Requisito | Estado | Notas |
|---|---|---|---|
| RF-01 | Iniciar sesión juego | ✅ Completado | Botón funcional en Main |
| RF-02 | Consumir API Dragon Ball | ✅ Completado | Usa `https://dragonball-api.com/api/` |
| RF-03 | Construir mazo de pares | ✅ Completado | Algoritmo de duplicación implementado |
| RF-04 | Mezclar y renderizar | ✅ Completado | `shuffled()` + `GridLayoutManager` |
| RF-05 | Lógica de volteo | ✅ Completado | Estados `isFaceUp` / `isMatched` |
| RF-06 | Registrar métricas | 🟡 Parcial | Intentos y tiempo (básico) |
| RF-08 | Configurar nivel | ✅ Completado | Guardado en Preferencias |
| RF-09 | Persistencia local | 🟡 Básico | SharedPreferences implementado |
| RF-10 | Caché de imágenes | ✅ Completado | Gestionado por Glide |

---

## 6. Integración con la Dragon Ball API
El backend se apoya en la API pública de Dragon Ball:

- **Endpoint principal:** `GET /characters`
- **Lógica implementada (v0.5):**
  1. Descarga un listado de personajes.
  2. Filtra los necesarios según dificultad.
  3. Duplica objetos para crear **pares**.
  4. Mezcla aleatoriamente y renderiza en `RecyclerView`.

---

## 7. Cómo ejecutar el proyecto (guía rápida)
1. Clona el repositorio y ábrelo con **Android Studio**.
2. Sincroniza Gradle.
3. Verifica dependencias: Retrofit, Gson Converter y Glide.
4. Ejecuta en un emulador o dispositivo físico Android.

> Si la app presenta errores de conectividad, revisa la **URL base** y permisos de red en el `AndroidManifest.xml` (por ejemplo, `INTERNET`).

---

## 8. Roadmap inmediato (sugerido)
- Persistencia avanzada con **Room** (sesiones, métricas, evolución por usuario).
- Manejo robusto de errores (offline-first / reintentos / mensajes amigables).
- Instrumentación de métricas terapéuticas: tiempo por intento, errores por nivel, progresión por sesión.
- UI/UX: transiciones, animaciones, sonidos y feedback multimodal.

---

## 9. Referencias
- Dragon Ball API (Documentación): https://web.dragonball-api.com/  
- Glide: https://github.com/bumptech/glide  
- Retrofit: https://square.github.io/retrofit/  

---

### Nota clínica/ética
NeuroPuzzles está concebido como **herramienta de apoyo** para intervención/estimulación y no reemplaza evaluación neuropsicológica ni tratamiento clínico integral. Su uso debe integrarse dentro de un plan terapéutico supervisado por un profesional.
