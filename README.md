# Huella de Carbono

Este proyecto es una aplicación para recopilar y gestionar las huellas de carbono generadas por los usuarios. Permite a los usuarios ver cuánto contaminan y les proporciona recomendaciones basadas en sus acciones más contaminantes.

## Características

- **Registro de Huellas de Carbono**: Los usuarios pueden registrar sus actividades y ver el impacto de sus acciones en términos de huella de carbono.
- **Recomendaciones**: Basado en las actividades más contaminantes de los usuarios, se generan recomendaciones para reducir su huella de carbono.
- **Gestión de Hábitos**: Los usuarios pueden registrar y gestionar sus hábitos para un seguimiento más detallado de su impacto ambiental.

## Tecnologías Utilizadas

- **Java**: Lenguaje principal de programación.
- **JavaFX**: Framework para la interfaz gráfica de usuario.
- **SQL**: Para la gestión de la base de datos.
- **Maven**: Herramienta de gestión de proyectos y dependencias.

## Estructura del Proyecto

- `src/main/java/com/github/JuanManuel/`: Contiene el código fuente principal de la aplicación.
  - `App.java`: Clase principal que inicia la aplicación.
  - `view/`: Contiene los controladores y vistas de la aplicación.
  - `model/`: Contiene las entidades y servicios del modelo de datos.

## Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/JuanManuel748/huella-de-carbono.git
   ```
2. Navega al directorio del proyecto:
   ```sh
   cd huella-de-carbono
   ```
3. Compila y ejecuta el proyecto con Maven:
   ```sh
   mvn clean install
   mvn javafx:run
   ```

## Uso

1. **Registro y Login**: Los usuarios pueden registrarse y acceder a la aplicación.
2. **Registro de Actividades**: Los usuarios pueden registrar sus actividades diarias y ver el impacto de cada una.
3. **Visualización de Huellas**: Los usuarios pueden ver un resumen de sus huellas de carbono y recibir recomendaciones para reducir su impacto.
4. **Gestión de Hábitos**: Los usuarios pueden añadir y gestionar sus hábitos para un seguimiento más detallado.

## Posibles Expansiones

- **Interfaz para Administradores**: Permitir a los administradores añadir más actividades contaminantes.
- **Comparación entre Usuarios**: Una interfaz donde los usuarios puedan comparar sus huellas de carbono con otros usuarios.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para discutir cualquier cambio que desees realizar.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

¡Gracias por usar nuestra aplicación para gestionar tu huella de carbono!
