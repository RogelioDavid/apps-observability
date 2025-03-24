# Usa una imagen de Node.js
FROM node:18

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar archivos al contenedor
COPY package.json package-lock.json ./
RUN npm install
RUN npm install express axios dotenv
RUN npm install express prom-client
RUN npm install winston
RUN npm install express-glass
RUN npm install log4js
RUN npm install --save @opentelemetry/api
RUN npm install --save @opentelemetry/auto-instrumentations-node
RUN npm install --save @opentelemetry/sdk-node
RUN npm install --save @opentelemetry/instrumentation-http 
RUN npm install --save @opentelemetry/instrumentation-express
RUN npm install --save @opentelemetry/exporter-trace-otlp-http 
RUN npm install --save @opentelemetry/exporter-metrics-otlp-http  
RUN npm install --save @opentelemetry/exporter-logs-otlp-http 


# Copiar el código fuente
COPY . .

# Exponer el puerto
EXPOSE 3000

# Comando para iniciar el servicio
CMD ["node", "--require"," ./src/tracing.js", "server.js"]