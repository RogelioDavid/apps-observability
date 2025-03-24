
//require("./src/tracing"); 
const express = require("express");
const { logWithTracing } = require("./src/logger");

const app = require("./src/app");

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
    logWithTracing("info", `Server is running on port ${PORT}`);
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});
