# Reto Nequi

![Nequi Logo](https://upload.wikimedia.org/wikipedia/commons/4/4b/Logo_nequi_morado.png)

---

## 🚀 Descripción del Proyecto

**Reto Nequi** es una aplicación de ejemplo para la gestión de  franquicias, sucursales y productos, desarrollada en Java con el stack reactive de Spring y Reactor. El objetivo es demostrar buenas prácticas de arquitectura hexagonal, pruebas unitarias y manejo de errores en un contexto de dominio bancario/financiero.

La aplicación ofrece servicios para:
- Crear y actualizar franquicias.
- Crear sucursales asociadas a franquicias.
- Agregar productos a sucursales y actualizar su stock.
- Consultar productos con mayor stock por sucursal y franquicia.

---

## 📂 Estructura del Proyecto

```
reto-nequi/
├── src/
│   ├── main/java/com/retonequi/domain/...
│   └── test/java/com/retonequi/domain/...
├── build.gradle
├── README.md
└── ...
```

---

## ⚙️ Tecnologías Utilizadas
- Java 17+
- Spring WebFlux (reactivo)
- Reactor (Mono/Flux)
- JUnit 5
- Mockito
- Gradle

---

## 🛠️ Instalación y Ejecución Local

### Prerrequisitos
- Java 17 o superior
- Gradle instalado (o usar el wrapper `./gradlew`)

### Pasos para correr la aplicación

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/EstebanRCarmona/reto-nequi.git
   cd reto-nequi
   ```
2. **Construye el proyecto:**
   ```bash
   ./gradlew build
   ```
3. **Ejecuta los tests:**
   ```bash
   ./gradlew test
   ```
4. **(Opcional) Corre la aplicación:**
   Si tienes un entrypoint (por ejemplo, un main o un endpoint REST), puedes correrlo con:
   ```bash
   ./gradlew bootRun
   ```

---

## 🧪 Pruebas
Las pruebas unitarias cubren todos los casos de negocio relevantes, incluyendo validaciones, errores y flujos exitosos. Puedes revisar los archivos en `src/test/java/com/retonequi/domain/services/`.

---

## 💡 Notas Adicionales
- El diseño sigue principios de arquitectura hexagonal y separación de capas.
- El código está preparado para ser fácilmente extensible y testeable.
- Si tienes dudas o sugerencias, ¡no dudes en abrir un issue o un pull request!

---

<div align="center">
  <b>Hecho con 💜 para el reto Nequi</b>
</div>