package br.com.armange.backend.api.snb.jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import br.com.armange.backend.api.snb.rest.resource.CityResource;

@ApplicationPath("/")
public class Application extends ResourceConfig {

    public Application() {
        loadResources();
        
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
    
    private void loadResources() {
        register(CityResource.class);
        register(MultiPartFeature.class);
        register(JacksonJaxbJsonProvider.class);
    }
}
