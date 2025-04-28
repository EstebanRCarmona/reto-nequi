# Reto nequi webflux
---

## 🚀 Descripción del Proyecto

**Reto Nequi** es una aplicación de ejemplo para la gestión de  franquicias, sucursales y productos, desarrollada en Java con el stack reactive de Spring y Reactor. El objetivo es demostrar buenas prácticas de arquitectura hexagonal, pruebas unitarias y manejo de errores en un contexto de dominio bancario/financiero.

La aplicación ofrece servicios para:
- Crear, obtener paginado y actualizar franquicias.
- Crear y obtener paginadas sucursales asociadas a franquicias.
- Agregar productos a sucursales, obtener productos de sucursales paginado y actualizar su stock.
- Consultar productos con mayor stock por sucursal y franquicia.

---

## 📂 Estructura del Proyecto

```
reto-nequi/
├── src/
│   ├── main/java/com/retonequi/
│   │   ├── domain/           # Lógica de negocio y entidades del dominio
│   │   ├── application/       # Lógica de aplicación (excepciones de aplicación)
│   │   └── infrastructure/   # Adaptadores de infraestructura (controladores, repositorios, utilidades externas)
│   └── test/java/com/retonequi/domain/services/ # Pruebas unitarias de servicios de dominio
├── build.gradle
├── README.md
└── ...

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
   Puedes correrlo con:
   ```bash
   ./gradlew bootRun
   ```
   luego se abrira el swagger y desde alli poder interactuar con la api.
   si no se abre, puedes ir manualmente a `http://localhost:7070/swagger-ui.html`

---

## 🧪 Pruebas
Las pruebas unitarias cubren todos los casos de negocio relevantes, incluyendo validaciones, errores y flujos exitosos. Puedes revisar los archivos en `src/test/java/com/retonequi/domain/services/`.

---

## 🚢 Despliegue con Docker y Terraform

### 🐳 Docker

1. **Construcción de la imagen:**
   ```sh
   docker build -t reto-nequi-api:webflux .
   ```
2. **Login en ECR:**
   ```sh
   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin "uri del repositorio en ecr"
   ```
3. **Taggear y subir la imagen:**
   ```sh
   docker tag reto-nequi-api:webflux "uri del ecr"
   docker push "la imagen que se tagueo anteriormente"
   ```

### ☁️ Terraform (Infraestructura en AWS)

1. **Pre-requisitos:**
   - Tener [Terraform](https://www.terraform.io/downloads.html) instalado.
   - Tener configuradas las credenciales de AWS (`aws configure`).
2. **Variables:**
   - Edita el archivo `terraform.tfvars` con los valores de tu entorno (VPC, subnets, etc).
3. **Inicializa y aplica:**
   ```sh
   cd terraform
   terraform init
   terraform plan
   terraform apply
   ```
4. **IMPORTANTE:**
   - **Antes de aplicar Terraform, debes subir manualmente la imagen Docker al repositorio ECR.**
   - Luego, asegúrate de actualizar la referencia de la imagen en el bloque `image` de la definición de task en Terraform (`main.tf`). Ejemplo:
     ```hcl
     image = "861286622325.dkr.ecr.us-east-1.amazonaws.com/reto-nequi-api:webflux"
     ```

5. **Recursos creados:**
   - ECS Cluster y Service
   - Application Load Balancer (ALB)
   - API Gateway HTTP
   - SSM Parameter Store (variables de entorno)
   - CloudWatch Log Group
   - Security Groups

6. **Destrucción:**
   ```sh
   terraform destroy
   ```

---

## 💡 Notas Adicionales
- El diseño sigue principios de arquitectura hexagonal y separación de capas.
- El código está preparado para ser fácilmente extensible y testeable.
- Si tienes dudas o sugerencias, ¡no dudes en abrir un issue o un pull request!

---