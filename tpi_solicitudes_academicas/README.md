# TPI Metodología de Sistemas II — Sistema de Gestión de Solicitudes Académicas

Evolución profesional de un sistema existente: calidad, deuda técnica, refactoring y revisión técnica. Entrega individual (justificación en el informe). Tecnología: Java, sin frameworks externos.

Aplicación de consola para gestionar solicitudes académicas: registrar una solicitud, cambiarle el estado, asignarle un responsable, buscarlas/filtrarlas y generar un reporte agregado. El detalle completo de cada etapa (diagnóstico, code smells, SOLID, refactorings, pruebas, code review, conclusiones) está en el informe PDF entregado junto a este repositorio.

## Estructura

```
src/
  Main.java        Punto de entrada
  model/            Entidades de dominio (Solicitud, enums, SolicitudRequest)
  repository/       Persistencia (interfaz + implementación en archivo)
  service/          Reglas de negocio, validación, reporte y presentación
tests/              Suite de 14 pruebas (sin JUnit: TestRunner por reflexión)
docs/
  code_review_checklist.md   Checklist usado en la revisión de código
  decisiones_tecnicas.md     Decisiones de diseño relevantes
  evidencias/                 Salidas reales de ejecución y de tests
```

## Compilar y ejecutar

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

## Correr las pruebas

```bash
javac -d out $(find src -name "*.java")
javac -cp out -d out $(find tests -name "*.java")
java -cp out TestRunner
```

Salida esperada: `Total: 14  OK: 14  Fallos: 0` (ver `docs/evidencias/tests_ejecucion.txt`).

## Tags por etapa

| Tag | Qué marca |
|---|---|
| `estado-inicial` | Versión base, con los 10 code smells documentados |
| `etapa-diagnostico` | Diagnóstico de calidad externa/interna y deuda técnica |
| `etapa-refactoring` | 5 refactorings aplicados, God Class eliminada |
| `etapa-pruebas` | Suite de 14 pruebas unitarias |
| `etapa-docs` | Checklist, code review y documentación completa |
| `estado-refactorizado` | Estado final: refactorizado, documentado y probado |