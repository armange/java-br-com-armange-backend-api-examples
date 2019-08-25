package br.com.armange.backend.api.upf.server.configuration;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.core.api.config.Configuration;

@Configuration(prefix=ServerProperties.PREFIX)
public interface ServerProperties {

    public static final String PREFIX = "br.com.armange";
    
    public static final String HTTP_SERVER_PORT = "http.server.port";
    public static final String LOG_LEVEL = "log.level";
    public static final String BASE_DIRECTORY = "base.directory";
    public static final String API_VERSION = "api.version";

    public static final String DEFAULT_HTTP_SERVER_PORT = "8080";
    public static final String DEFAULT_LOG_LEVEL = "ERROR";
    
    @ConfigProperty(name = HTTP_SERVER_PORT, defaultValue = DEFAULT_HTTP_SERVER_PORT)
    Integer httpServerPort();
    
    @ConfigProperty(name = LOG_LEVEL, defaultValue = DEFAULT_LOG_LEVEL)
    Integer logLevel();
    
    @ConfigProperty(name = BASE_DIRECTORY)
    String baseDirectory();
    
    @ConfigProperty(name = API_VERSION, defaultValue=StringUtils.EMPTY)
    String apiVersion();
}
