# Usar una imagen base oficial de Java para producción
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR construido al contenedor
COPY build/libs/reto-nequi-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre la app
EXPOSE 7070

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]
