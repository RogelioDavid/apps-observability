require("./tracing"); // Initialize OpenTelemetry
const { logWithTracing } = require("./logger");

const express = require("express");
const userRoutes = require("./routes/userRoutes");
require("dotenv").config();
const client = require("prom-client");
const app = express();
const axios = require('axios');
var log4js = require('log4js');

const API_BASE_URL = process.env.API_BASE_URL;

  // Define métricas personalizadas
  const httpRequestCounter = new client.Counter({
    name: "client_requests_total",
    help: "Total de solicitudes HTTP recibidas",
    labelNames: ["service_name", "path", "status"],
  });
  
  const httpRequestDuration = new client.Histogram({
    name: "client_request_duration_histogram",
    help: "Duración de las solicitudes HTTP en segundos",
    labelNames: ["service_name", "path", "status"],
    buckets: [0.1, 0.3, 0.5, 1, 1.5, 2, 5],
  });

// Endpoint para exponer métricas
app.get("/metrics", async (req, res) => {
    res.set("Content-Type", client.register.contentType);
    res.end(await client.register.metrics());
  });

  app.get("/health/liveness", async (req, res) => {
   
    res.status(200).json(JSON.stringify({ status: "UP" }));
  });
      
// Middleware para parsear JSON
app.use(express.json());


// Middleware para medir el tiempo de respuesta
app.use((req, res, next) => {
  logWithTracing("info", "Processing request at root path");
    const end = httpRequestDuration.startTimer();
    res.on("finish", () => {
      httpRequestCounter.inc({
        service_name: 'ms-user-services',
        path: req.path,
        status: res.statusCode,
      });
  
      end({ service_name: 'ms-user-services', path: req.path, status: res.statusCode });
    });
    next();
  });


 
  
// Rutas
app.use("/api/users", userRoutes);

module.exports =  app ; 