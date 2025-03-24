package org.grafana.config.filter;


import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class SmartLogger {

	public static SmartLoggerBean info() {
        return new SmartLoggerBean(LogLevels.INFO);
    }

    public static SmartLoggerBean error() {
        return new SmartLoggerBean(LogLevels.ERROR);
    }

    public static SmartLoggerBean warn() {
        return new SmartLoggerBean(LogLevels.WARN);
    }

    public static SmartLoggerBean debug() {
        return new SmartLoggerBean(LogLevels.DEBUG);
    }

    public static final class SmartLoggerBean {

        private final LogLevels level;
        private Exception ex;
        private Map<String, Object> logs = new LinkedHashMap<>();

        SmartLoggerBean(LogLevels level) {
            this.level = level;
        }

        public SmartLoggerBean withAction(String action) {
            logs.put("action", action);
            return this;
        }

        public SmartLoggerBean withDescription(String action) {
            logs.put("description", action);
            return this;
        }

        public SmartLoggerBean with(String key, Object value) {
            logs.put(key, value);

            return this;
        }

        public SmartLoggerBean with(Exception ex) {
            this.ex = ex;
            return this;
        }

        public SmartLoggerBean withMap(Map<String, Object> map) {
            logs.putAll(map);

            return this;
        }

        public String toString() {
            return createStringFromMap();
        }

        private String createStringFromMap() {
            final StringBuilder messageToLog = new StringBuilder();

            this.logs.forEach((key, value) -> {
                if (!Objects.isNull(value)) {
                    messageToLog.append(" ").append(key).append("=").append(stringify(value));

                }
            });

            return messageToLog.toString().trim();
        }

        static String stringify(Object obj) {
            if (obj == null) {
                return null;
            }

            String result = obj.toString();

            if (result.contains(" ")) {
                result = escaped(result);
            }
            return result;
        }

        public void to(Logger log) {
            this.level.write(this.toString(), this.ex, log);
        }
    }

    public static String escaped(String value) {
        return String.format("\"%s\"", value.replace('"', '\''));
    }

    private enum LogLevels {
        INFO {
            @Override
            public void write(String msg, Exception ex, Logger log) {
                if (ex != null) {
                    log.info(msg, ex);
                } else {
                    log.info(msg);
                }
            }
        }, ERROR {
            @Override
            public void write(String msg, Exception ex, Logger log) {
                if (ex != null) {
                    log.error(msg, ex);
                } else {
                    log.error(msg);
                }
            }
        }, WARN {
            @Override
            public void write(String msg, Exception ex, Logger log) {
                if (ex != null) {
                    log.warn(msg, ex);
                } else {
                    log.warn(msg);
                }
            }
        }, DEBUG {
            @Override
            public void write(String msg, Exception ex, Logger log) {
                if (ex != null) {
                    log.debug(msg, ex);
                } else {
                    log.debug(msg);
                }
            }
        };

        public abstract void write(String msg, Exception ex, Logger log);
    }
}
