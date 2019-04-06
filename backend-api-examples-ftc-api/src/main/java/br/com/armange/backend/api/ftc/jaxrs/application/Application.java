package br.com.armange.backend.api.ftc.jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import br.com.armange.backend.api.ftc.rest.resource.ColorResource;
import br.com.armange.backend.api.ftc.rest.resource.VeicleResource;

@ApplicationPath("/")
public class Application extends ResourceConfig {

    public Application() {
        loadResources();
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
    
    private void loadResources() {
        register(VeicleResource.class);
        register(ColorResource.class);
        register(JacksonJaxbJsonProvider.class);
    }
}