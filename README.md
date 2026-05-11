ApiGateway

Microservicio Spring Cloud Gateway encargado de enrutar y asegurar las llamadas entre los microservicios del sistema: KPI, Data y Dashboard. Gestiona validación JWT, CORS global y reglas de enrutamiento configurables mediante variables de entorno.

Requisitos
- Java 21
- Maven
- Los microservicios destino (KPI, Data, Dashboard) accesibles vía las URIs configuradas
- Variables de entorno para configuración de OAuth2/JWT y URIs de servicios

Instalación y ejecución
1. Compilar:

```
mvn clean install
```

2. Ejecutar:

```
mvn spring-boot:run
```

Por defecto el servicio se ejecuta en: http://localhost:9000

Configuración
Define las siguientes variables de entorno antes de iniciar el servicio:

- `OAUTH2_ISSUER_URI` - URI del emisor (issuer) para la validación de tokens JWT.
- `OAUTH2_AUDIENCE` - Audience esperado en los JWT.
- `AUTH0_ROLES_CLAIM` - Nombre del claim que contiene los roles (p. ej. `roles`).
- `KPI_SERVICE_URI` - URI del servicio KPI (p. ej. `http://kpi-service:8081`).
- `DATA_SERVICE_URI` - URI del servicio Data (p. ej. `http://data-service:8091`).
- `DASHBOARD_SERVICE_URI` - URI del servicio Dashboard (p. ej. `http://dashboard-service:8082`).
- `FRONTEND_ORIGIN` - Origen permitido para CORS (p. ej. `http://localhost:3000`).

Funcionalidades principales
- Enrutamiento de peticiones hacia microservicios remotos (`/api/kpi/**`, `/api/data/**`, `/api/dashboard/**`).
- Eliminación de prefijo (`StripPrefix`) para exponer rutas limpias a los servicios destino.
- Validación de tokens JWT como `resource-server` usando el `issuer-uri` configurado.
- Configuración de CORS global y control de orígenes permitidos.
- Todas las rutas y URIs son configurables mediante variables de entorno para facilitar despliegues en distintos entornos.

Notas
- Las rutas principales se definen en `src/main/resources/application.yml` y se pueden sobrescribir mediante las variables de entorno indicadas.
- Ajusta `server.port` en `application.yml` o mediante la variable `SERVER_PORT` si necesitas cambiar el puerto por defecto.

Recursos
- Archivo de configuración: [src/main/resources/application.yml](src/main/resources/application.yml)

