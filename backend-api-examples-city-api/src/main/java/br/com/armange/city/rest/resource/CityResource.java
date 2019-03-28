package br.com.armange.city.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/city")
public class CityResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayPlainTextHello() {
      return "Hello";
    }
}
