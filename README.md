# NeuroPuzzles (Borrador inicial - Módulo 1)

## 1. Descripción del proyecto
NeuroPuzzles es una aplicación móvil Android orientada a la gamificación terapéutica, diseñada como un juego de memoria tipo “voltear cartas y encontrar pares” para estimular y entrenar procesos atencionales y mnésicos en niños de 6 a 11 años, particularmente en contextos de intervención clínica con dificultades atencionales asociadas a TDAH. El núcleo lúdico se basa en sesiones breves con retroalimentación inmediata, registro de desempeño y progresión por dificultad, priorizando una experiencia clara, segura y atractiva para población infantil.

Para la obtención de imágenes se integrará una API pública temática de Dragon Ball, de la cual se consumirán imágenes de personajes. Estas imágenes se transformarán en “cartas” y se distribuirán aleatoriamente en el tablero según el nivel elegido. La aplicación incorporará mecanismos básicos de control por parte del terapeuta (interfaz de administrador) para adaptar la sesión a la edad, tolerancia a la frustración y objetivos clínicos del niño.

## 2. Exposición del problema
En consulta clínica con niños con TDAH, una dificultad habitual es sostener la atención, regular la impulsividad durante tareas monótonas y mantener el compromiso con ejercicios repetitivos. Esto afecta tanto la adherencia al tratamiento como la generalización de estrategias atencionales. Desde un enfoque aplicado, una intervención gamificada puede favorecer la motivación intrínseca, ofrecer refuerzo inmediato y estructurar retos graduados que permitan entrenar memoria visual y atención sostenida/selectiva en un formato natural para el niño.

NeuroPuzzles se plantea como una herramienta complementaria dentro de sesiones guiadas, para: (a) incrementar el tiempo de permanencia en tarea, (b) ejercitar memoria de trabajo y memoria visual, (c) registrar indicadores simples (tiempo, intentos, aciertos) que permitan observar tendencias de progreso en el contexto terapéutico.

## 3. Plataforma
- Plataforma objetivo: Android (teléfonos y tablets).
- IDE: Android Studio.
- Lenguaje: Kotlin.
- UI: XML (Views) o Jetpack Compose (a definir según requisitos del curso).
- Arquitectura recomendada: MVVM (ViewModel + Repository).
- Consumo de API: Retrofit + Kotlin Coroutines.
- Persistencia local (borrador): DataStore para preferencias y Room para historial (opcional en esta primera versión).
- Control de versiones: Git y GitHub (GitHub Classroom).

## 4. Interfaz de usuario e interfaz de administrador

### 4.1 Interfaz de usuario (niño)
Interfaz minimalista con navegación lineal y estímulos visuales claros: pantalla de inicio, selección de nivel (si no está bloqueado), tablero del juego y pantalla de resultados. Se priorizarán botones grandes, tipografía legible y retroalimentación inmediata (sonidos opcionales, animación breve al acertar, mensajes cortos).

### 4.2 Interfaz de administrador (terapeuta)
Permite configurar la sesión sin exponer ajustes complejos al niño. Funciones previstas:
- Seleccionar rango de edad o perfil del niño.
- Definir nivel de dificultad y/o bloquear niveles.
- Establecer duración de sesión (ej. 3–7 minutos) o condición de término (pares completados).
- Activar/desactivar ayudas (pista breve, previsualización inicial, límite de errores).
- Revisar historial simple de desempeño (últimas sesiones).

## 5. Funcionalidad

### 5.1 Requisitos funcionales (RF)
- RF-01: Iniciar una sesión desde pantalla principal.
- RF-02: Consumir la API de Dragon Ball para obtener imágenes de personajes.
- RF-03: Construir un mazo con pares duplicando imágenes seleccionadas.
- RF-04: Mezclar cartas y renderizar tablero según el nivel.
- RF-05: Voltear dos cartas por intento; si coinciden quedan visibles; si no, se ocultan tras breve intervalo.
- RF-06: Registrar métricas por sesión: tiempo total, intentos, aciertos/errores.
- RF-07: Mostrar pantalla de resultados al finalizar.
- RF-08: Permitir al administrador configurar nivel, duración, ayudas y perfil.
- RF-09: Guardar localmente configuraciones e historial básico.
- RF-10: Manejar caché de imágenes (reutilizar si falla internet).

### 5.2 Requisitos no funcionales (RNF)
- RNF-01: Usabilidad infantil (botones grandes, mínima lectura, feedback inmediato).
- RNF-02: Rendimiento (carga optimizada, paginación/limitación y caché).
- RNF-03: Confiabilidad (manejo de errores de red, sin cierres inesperados).
- RNF-04: Privacidad (no almacenar datos sensibles del niño; métricas sin identificación personal).
- RNF-05: Mantenibilidad (arquitectura por capas, código documentado, control de versiones).

## 6. Diseño (wireframes / esquemas de página)

### 6.1 Flujo principal (niño)
| Pantalla | Elementos principales | Objetivo |
|---|---|---|
| Pantalla 1 - Splash | Logo + nombre, carga breve | Inicio, verificación de conectividad/caché |
| Pantalla 2 - Inicio | Botón Iniciar, Botón Administrador, Acerca de | Punto de entrada |
| Pantalla 3 - Selección de nivel | Fácil/Medio/Difícil (con bloqueo opcional) | Selección de dificultad |
| Pantalla 4 - Tablero | Grid de cartas, intentos, timer, pausa | Juego principal |
| Pantalla 5 - Resultados | Tiempo, intentos, errores, Reiniciar/Inicio | Resumen por sesión |

### 6.2 Administración (terapeuta)
| Pantalla | Elementos principales | Objetivo |
|---|---|---|
| Pantalla 6 - Admin (PIN simple) | PIN, acceso a configuración | Evitar cambios por el niño |
| Pantalla 7 - Configuración | Edad/perfil, nivel, duración, ayudas, guardar | Configurar sesión |
| Pantalla 8 - Historial | Lista de sesiones, detalle | Seguimiento básico |

## 7. Niveles de dificultad propuestos (memoria por edad)
La dificultad se define por el tamaño del tablero (cantidad de cartas) y el número de pares a recordar. Se propone una gradación estándar usada en juegos de memoria: 4x4 (fácil), 6x6 (medio) y 8x8 (difícil).

| Nivel | Tablero | Cartas / pares | Rango etario sugerido (guía) |
|---|---:|---:|---|
| Fácil | 4x4 | 16 cartas / 8 pares | 6–7 años (inicio) o mayor dificultad atencional |
| Medio | 6x6 | 36 cartas / 18 pares | 8–9 años (promedio) o mejor tolerancia |
| Difícil | 8x8 | 64 cartas / 32 pares | 10–11 años (avanzado) o mayor desafío |

Nota: la app permitirá ajustar el número de pares dentro de cada nivel si se requiere adaptación clínica (ej. 6–10 pares en Fácil), gestionado desde la interfaz de administrador.

## 8. Integración con API de Dragon Ball (backend e imágenes)
Se consumirá el endpoint de personajes para recuperar un listado paginado y seleccionar un subconjunto de imágenes (ej. 8, 18 o 32 personajes según nivel). Flujo propuesto:
1) Solicitar lista de personajes
2) Filtrar los que tienen imagen válida
3) Seleccionar aleatoriamente N personajes
4) Crear pares duplicando la lista
5) Mezclar y renderizar el tablero

## 9. Alcance inicial y entregables en GitHub
En este módulo se entrega el borrador en formato README dentro del repositorio de GitHub Classroom. En el siguiente módulo se ampliará con detalles técnicos de implementación, estructura del proyecto Android y evidencias (capturas o wireframes gráficos si aplica).

## 10. Referencias consultadas (para este borrador)
- Documentación oficial Dragon Ball API: https://web.dragonball-api.com/documentation
- Swagger/OpenAPI Dragon Ball API: https://dragonball-api.com/api-docs
- Ejemplo de niveles 4x4 / 6x6 / 8x8 en juegos de memoria: https://play.google.com/store/apps/details?id=com.pinkpointer.memory
- Ejemplo adicional de gradación 4x4/6x6/8x8: https://play.google.com/store/apps/details?id=com.drill.matchit
