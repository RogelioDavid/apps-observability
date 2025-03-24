const User = require("../models/userModel");
const axios = require('axios');
const client = require("prom-client");
//require("../tracing"); // Initialize OpenTelemetry
const { logWithTracing } = require("../logger");
const express = require("express");

let users = [new User(1, "rogelio", "12-12-2024"),new User(2, "david", "13-12-2024")]; // Almacenamiento temporal

// Obtener todos los usuarios
const getUsers = (req, res) => {
    obtenerUsuarios();
      
    res.json(users);
};

// Crear un nuevo usuario
const createUser = (req, res) => {
    const { id, name, birthdate } = req.body;

    if (!id || !name || !birthdate) {
        return res.status(400).json({ message: "Todos los campos son requeridos" });
    }

    const newUser = new User(id, name, birthdate);
    users.push(newUser);

    res.status(201).json(newUser);
};


const obtenerUsuarios = async () => {
    try {
      const response = await axios.get('http://ms-hello-observability-services.applications.svc.cluster.local:8080/start-all');
      logWithTracing("info", "Processing request obtenerUsuarios");
      console.log(response.data);
    } catch (error) {
       logWithTracing("error", 'Error al obtener usuarios:' + error.message);
           
    }
  };


  // Define métricas personalizadas
const httpRequestCounter = new client.Counter({
  name: "requests_total",
  help: "Total de solicitudes HTTP recibidas",
  labelNames: ["service_name", "path", "status"],
});

const httpRequestDuration = new client.Histogram({
  name: "request_duration_histogram",
  help: "Duración de las solicitudes HTTP en segundos",
  labelNames: ["service_name", "path", "status"],
  buckets: [0.1, 0.3, 0.5, 1, 1.5, 2, 5],
});



  
module.exports = { getUsers, createUser };
