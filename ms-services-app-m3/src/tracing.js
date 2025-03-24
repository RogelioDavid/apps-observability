const process = require("process");
const opentelemetry = require("@opentelemetry/sdk-node");
const {
  getNodeAutoInstrumentations,
} = require("@opentelemetry/auto-instrumentations-node");
const {
  OTLPTraceExporter,
} = require("@opentelemetry/exporter-trace-otlp-http");
const { Resource } = require("@opentelemetry/resources");
const {
  SemanticResourceAttributes,
} = require("@opentelemetry/semantic-conventions");

// Configure the OTLP exporter
const traceExporter = new OTLPTraceExporter({
  url: process.env.OTEL_EXPORTER_OTLP_ENDPOINT || "http://localhost:4318/v1/traces",
});

// Create and configure OpenTelemetry SDK
const sdk = new opentelemetry.NodeSDK({
  resource: new Resource({
    [SemanticResourceAttributes.SERVICE_NAME]: "nodejs-auto-instrumentation-service",
  }),
  traceExporter,
  instrumentations: [getNodeAutoInstrumentations()],
});

// Start the SDK
sdk.start();

// Ensure OpenTelemetry is properly shut down on process exit
process.on("SIGTERM", () => {
  sdk.shutdown().then(() => console.log("OpenTelemetry shut down")).finally(() => process.exit(0));
});
