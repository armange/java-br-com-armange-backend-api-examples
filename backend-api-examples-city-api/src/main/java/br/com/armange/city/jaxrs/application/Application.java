package br.com.armange.city.jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import br.com.armange.city.rest.resource.Hello;

@ApplicationPath("/")
public class Application extends ResourceConfig {

    public Application() {
        loadResources();
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
    
    private void loadResources() {
        register(Hello.class);
    }
}
