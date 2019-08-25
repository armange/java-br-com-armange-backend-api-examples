package br.com.armange.backend.api.upf.server.http.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.glassfish.jersey.servlet.ServletContainer;

import br.com.armange.backend.api.upf.jaxrs.application.Application;
import br.com.armange.backend.api.upf.server.configuration.PropertyKeyHandler;
import br.com.armange.backend.api.upf.server.configuration.ServerProperties;

public class TomcatServer {
    
    private static TomcatServer INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger(TomcatServer.class);
    private static final String SERVLET_NAME = "br.com.armange.jaxrs.Application";
    private final String contextPath = "/";
    private final String appBase = ".";
    
    private Tomcat tomcat;
    private Context context;
    
    private TomcatServer() {
        configureLogLevel();
    }

    private void configureLogLevel() {
        final String logLevel = ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.LOG_LEVEL))
                .as(String.class)
                .withDefault(ServerProperties.DEFAULT_LOG_LEVEL)
                .getValue()
                .toUpperCase();
        
        Configurator.setRootLevel(Level.getLevel(logLevel));
    }
    
    public void start() throws LifecycleException {
        createAndConfigureTomcatServer();
        
        configureServerContext();
        
        addServletsToTomcatServer();
        
        startTomcatServer();
        
        logServerStatus();
    }

    private void createAndConfigureTomcatServer() {
        final Integer port = ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.HTTP_SERVER_PORT))
                .as(Integer.class)
                .withDefault(Integer.parseInt(ServerProperties.DEFAULT_HTTP_SERVER_PORT))
                .getValue();
        
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(appBase);
    }
    
    private void addServletsToTomcatServer() {
        
        Tomcat.addServlet(context, SERVLET_NAME, buildServletContainer());
        context.addServletMapping(StringUtils.join("/", getApiVersion(), "*"), SERVLET_NAME);
    }
    
    private String getApiVersion() {
        return ConfigResolver
            .resolve(PropertyKeyHandler.build(ServerProperties.API_VERSION))
            .as(String.class)
            .getValue();
    }

    private void configureServerContext() {
        context = tomcat.addWebapp(contextPath, appBase);
        
        configureCORS();
    }

    private void configureCORS() {
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterClass("org.apache.catalina.filters.CorsFilter");
        filterDef.setFilterName("CorsFilter");
        
        filterDef.addInitParameter("cors.allowed.origins", "*");
        filterDef.addInitParameter("cors.allowed.methods", "GET,POST,HEAD,OPTIONS,PUT");
        
        context.addFilterDef(filterDef);
        
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("CorsFilter");
        filterMap.addURLPattern("*");
        
        context.addFilterMap(filterMap);
    }
    
    private static ServletContainer buildServletContainer() {
        return new ServletContainer(new Application());
    }

    private void startTomcatServer() {
        final Thread tomcatThread = new Thread(() -> {
            try {
                tomcat.start();
                tomcat.getServer().await();
            } catch (final LifecycleException e) {
                e.printStackTrace();
            }
        });
        
        tomcatThread.start();
    }
    
    private void logServerStatus() {
        while(!tomcat.getServer().getState().equals(LifecycleState.STARTED)) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Started successfully!!");
    }

    public static TomcatServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TomcatServer();
        }
        
        return INSTANCE;
    }
    
    public static void main(String[] args) throws LifecycleException {
        TomcatServer.getInstance().start();
    }
}