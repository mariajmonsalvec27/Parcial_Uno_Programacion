# LinguaPlus – Parcial 1 Programación II

Sistema de gestión para la academia de idiomas **LinguaPlus**, hecho en Java 21 + JavaFX con arquitectura MVC.

- Diagrama de clases: [`docs/diagrama.puml`](docs/diagrama.puml)
- Pensamiento computacional: [`docs/pensamiento-computacional.md`](docs/pensamiento-computacional.md)

## Requisitos
- JDK 21 o superior (probado con 21; funciona con 24 y 26).
- Maven. IntelliJ ya lo trae incluido.

## Cómo ejecutar
**Desde IntelliJ:** abrir el proyecto, recargar Maven (clic derecho en `pom.xml` → Maven → Reload project) y ejecutar la clase `Launcher`.

**Desde la terminal:**
```bash
mvn clean javafx:run
```

## Cómo correr las pruebas
```bash
mvn test
```
En IntelliJ también se puede hacer clic derecho en `src/test/java` → *Run 'All Tests'*.

## Qué hay en la aplicación
| Pestaña | Funcionalidad |
|---|---|
| Estudiantes, Docentes, Programas, Servicios | CRUD (agregar, actualizar, eliminar, limpiar) |
| Matrículas | Registrar una matrícula con el Builder y generar el comprobante en PDF o Excel |
| Consultas | Buscar un estudiante por teléfono (dice si el número es perfecto) e ingresos por periodo |

La aplicación arranca con datos de ejemplo. El teléfono **33550336** de Ana Gómez es un número perfecto.
Los comprobantes se guardan en la carpeta `comprobantes/` y el consecutivo de matrículas en `datos/consecutivo.txt`.

## Estructura (MVC)
```
src/main/java/co/edu/uniquindio/poo/parcial_uno_programacion/
├── model/         entidades, reglas de negocio y patrones (no conoce JavaFX)
├── interfaces/    interfaces pequeñas (ISP)
├── repositorio/   repositorio genérico en memoria
├── controller/    controladores JavaFX (uno por pestaña)
└── view/          MainApp (+ FXML en src/main/resources/.../view)
```

## Patrones de diseño
| Patrón | Clase | Para qué |
|---|---|---|
| Singleton | `GeneradorConsecutivo`, `Academia` | Número de matrícula que nunca se repite; una sola academia |
| Factory Method | `ProgramaFactory` + `FabricaPrograma*` | Crear programas básico, intensivo o personalizado con sus beneficios |
| Abstract Factory | `MaterialEntregaFactory` + `FabricaPresencial`/`FabricaVirtual` | Material y carné siempre de la misma modalidad |
| Prototype | `OfertaPrograma.clonar()`, `PeriodoAcademico.clonar()` | Abrir un periodo con la misma oferta y cupos independientes |
| Builder | `Matricula.Builder` | Datos obligatorios y opcionales, y validación del descuento máximo de 30 % |
