# Checklist de code review

Checklist técnico utilizado para revisar el estado final del repositorio (tag `etapa-pruebas`), organizado en las categorías habituales de una revisión profesional de código.

## 1. Diseño y responsabilidades

- [ ] Cada clase tiene una única responsabilidad clara y un nombre que la describe.
- [ ] No hay estado mutable compartido de forma global (`static` usado como variable, no como constante).
- [ ] Las dependencias entre módulos van hacia abstracciones (interfaces), no hacia implementaciones concretas, en los puntos donde eso importa para testear o para poder cambiar de tecnología.
- [ ] No hay métodos que mezclen niveles de abstracción distintos (ej. validar + persistir + loggear en el mismo bloque).

## 2. Nombres y legibilidad

- [ ] Los nombres de clases, métodos y variables describen intención, no implementación.
- [ ] No hay números ni strings sin una constante o un enum que les dé significado.
- [ ] Los métodos son cortos y se leen como una secuencia de pasos de alto nivel.

## 3. Manejo de errores

- [ ] Ninguna excepción se atrapa y se descarta en silencio (`catch` vacío o solo con `printStackTrace()` sin relanzar).
- [ ] Los casos de error de negocio (dato inválido, entidad no encontrada) se comunican con tipos específicos, no con códigos de retorno (`null`, `boolean`, `-1`) fáciles de ignorar.
- [ ] Los mensajes de excepción son informativos (incluyen qué dato falló y por qué).

## 4. Encapsulamiento

- [ ] No hay campos públicos mutables en las clases de dominio.
- [ ] La mutación de un objeto de dominio solo es posible a través de métodos que validan o tienen sentido de negocio (no un setter genérico para cada campo).
- [ ] Ninguna clase externa necesita conocer la estructura interna de otra para operar sobre ella (ausencia de Feature Envy).

## 5. Pruebas

- [ ] La lógica de negocio se puede probar sin tocar el disco, la red ni ningún recurso externo real.
- [ ] Existen pruebas para el camino feliz y para al menos un camino de error de cada operación relevante.
- [ ] Las pruebas son deterministas (no dependen de orden de ejecución ni de estado dejado por otra prueba).

## 6. Persistencia y acoplamiento con I/O

- [ ] La lógica de negocio no abre archivos, conexiones ni streams directamente.
- [ ] Cambiar el mecanismo de persistencia no debería requerir tocar las reglas de negocio.

## 7. Historial de cambios

- [ ] Cada commit deja el proyecto en un estado que compila y corre.
- [ ] Los mensajes de commit explican el "por qué" del cambio, no solo el "qué".
- [ ] Hay evidencia verificable (salida de ejecución, resultado de tests) de que un refactor no cambió el comportamiento observable.
