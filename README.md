# PokeApp - Desafío de Testing en Android

Este proyecto es una aplicación Android que consume la PokeAPI y demuestra la implementación de arquitectura limpia, persistencia local con Room y, fundamentalmente, una suite de **Pruebas Unitarias** robustas.

## 🚀 Desafío de Testing
Se ha implementado una capa de pruebas unitarias para el `UserRepository` utilizando las siguientes tecnologías:
*   **JUnit 4**: Estructura base de los tests.
*   **Mockito**: Para realizar mocks del DAO (`UserDao`) y verificar interacciones con la base de datos local.
*   **MockWebServer**: Para simular un servidor web real y probar la lógica de Retrofit sin dependencia de una conexión a internet activa.
*   **InstantTaskExecutorRule**: Para garantizar la ejecución síncrona de las tareas de LiveData.

### Escenarios de Prueba Cubiertos
1.  **Éxito en API**: Verificación de que, tras una respuesta HTTP 200, los datos se emiten correctamente a través de LiveData y se insertan en la base de datos local (Room).
2.  **Error en API**: Verificación de que, ante un error HTTP 500 o fallo de red, el sistema maneja la excepción, emite un valor nulo y **nunca** intenta guardar datos erróneos en la base de datos local.

## 🛠️ Tecnologías y Arquitectura
*   **Arquitectura**: MVVM con Pattern Repository.
*   **Networking**: Retrofit 2 + Gson.
*   **Base de Datos**: Room Persistence Library (migrado a KAPT para estabilidad de build).
*   **Lenguaje**: Kotlin & Java.
*   **Gradle**: Configuración optimizada (v8.7) para garantizar compilación limpia y 0 errores de configuración.

## 🧹 Calidad de Código
El proyecto cumple con el estándar de **0 errores y 0 advertencias (warnings)** de Lint, garantizando un código mantenible y libre de malas prácticas detectables por análisis estático.

---
*Desarrollado como parte del ejercicio de Consumo de API Rest - M6.*
