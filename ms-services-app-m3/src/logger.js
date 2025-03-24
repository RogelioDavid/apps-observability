const log4js = require("log4js");
const { context, trace } = require("@opentelemetry/api");

// log4js.configure({
//   appenders: {
//     out: { type: "stdout", layout: { type: "json", separator: "," } },
//   },
//   categories: { default: { appenders: ["out"], level: "info" } },
// });

log4js.addLayout('json', function(config) {
    return function(logEvent) { return JSON.stringify(logEvent); }
  });
  
  log4js.configure({
    appenders: {
      out: { 
        type: 'file',
        filename: 'app.log', 
        keepFileExt: true,
        maxLogSize: 100000,
        numBackups: 1000,
        layout: { type: 'json' }
      },
      out2: { 
        type: 'stdout',
        keepFileExt: true,
        maxLogSize: 100000,
        numBackups: 1000,
        layout: { type: 'json' }
      }      
    },
    categories: {
      default: { appenders: ['out','out2'], level: 'info' }
    }
  });
  
const logger = log4js.getLogger();

/**
 * Function to format logs with traceId and spanId
 */
function logWithTracing(level, message) {
  const spanContext = trace.getSpan(context.active())?.spanContext();
  const traceId = spanContext ? spanContext.traceId : "unknown";
  const spanId = spanContext ? spanContext.spanId : "unknown";

  logger[level]({ message, traceId, spanId });
}

module.exports = { logger, logWithTracing };
