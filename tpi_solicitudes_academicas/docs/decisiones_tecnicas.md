# Decisiones técnicas

Registro de las decisiones de diseño relevantes tomadas durante el desarrollo de este TPI, con su justificación, para que queden documentadas y puedan defenderse.

## Lenguaje y entorno

- **Java, sin frameworks externos.** Se eligió Java puro (sin Spring ni ningún framework de inyección de dependencias) porque el tamaño del sistema no lo justifica, y porque el objetivo del TPI es mostrar manejo de SOLID/Clean Code "a mano", no el uso de un framework que resuelva la inyección de dependencias por otro.
- **Sin JUnit.** El entorno de desarrollo usado no tiene acceso a un repositorio de dependencias para instalar JUnit, así que se optó por un mini-framework de pruebas propio (`tests/Assert.java` + `tests/TestRunner.java` con descubrimiento por reflexión), suficiente para los 14 casos de prueba de este proyecto.
- **Persistencia en archivo de texto plano, no base de datos.** Se mantuvo la misma estrategia de persistencia de la versión base (no se cambia la tecnología de persistencia como parte del refactor, para no mezclar "refactor" con "nueva funcionalidad"). El diseño de `SolicitudRepository` como interfaz es, precisamente, lo que deja la puerta abierta a reemplazarla por una base de datos en una iteración futura sin tocar `SolicitudService`.

## Estructura de paquetes

Se eligió separar en tres paquetes (`model`, `repository`, `service`) en vez de uno solo, para que la estructura de carpetas comunique por sí misma la separación de responsabilidades, cualquiera que abra el proyecto ve de inmediato que la representación de datos, el acceso a persistencia y las reglas de negocio están separados, sin necesidad de leer el código.

## Excepciones checked vs. unchecked

Se eligieron excepciones **checked** (`SolicitudInvalidaException`, `SolicitudNoEncontradaException`, `PersistenciaException` extienden `Exception`, no `RuntimeException`) a propósito, aunque la tendencia moderna en muchos equipos es preferir unchecked. La razón: para los fines pedagógicos de este TPI, se buscó que el propio compilador obligara a manejar explícitamente cada caso de error en `Main`, de forma que fuera imposible "olvidarse" de un caso al actualizar los puntos de llamada durante el refactor. En un sistema de mayor escala, con más capas entre el origen del error y quien lo maneja, esta misma decisión podría revisarse a favor de excepciones unchecked para no forzar firmas de métodos a propagar `throws` en cascada.

## Por qué no se agregó un Builder para SolicitudRequest

`SolicitudRequest` tiene 7 campos y un único constructor con todos ellos. Se evaluó agregar un patrón Builder, pero se descartó, con 7 campos y un solo punto de construcción en todo el código (`Main`, y los tests), un Builder habría agregado una clase más sin resolver un problema real todavía presente (no hay sobrecarga de constructores ni combinaciones opcionales de parámetros). Se prefirió no agregar complejidad especulativa (YAGNI) y dejarlo como una mejora a aplicar si el número de campos opcionales creciera en el futuro.

## Por qué `ReporteSolicitudes` es un objeto de valor inmutable

Se eligió que `ReporteSolicitudes` solo tenga getters y ningún setter, construido completo en su único constructor. Al ser el resultado de un cálculo (no una entidad con ciclo de vida propio), no hay ninguna razón de negocio para mutarlo después de creado; hacerlo inmutable elimina de raíz la posibilidad de que un reporte ya calculado sea modificado por error en algún punto intermedio antes de mostrarse.
