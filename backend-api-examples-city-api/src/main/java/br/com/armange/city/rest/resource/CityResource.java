package br.com.armange.city.rest.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.com.armange.codeless.objectbuilder.ObjectBuilder;
import br.com.armange.codeless.objectbuilder.csv.CsvLine;

@Path("/city")
public class CityResource {

    @POST
    @Path("/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
        @FormDataParam("file") final InputStream uploadedInputStream,
        @FormDataParam("file") final FormDataContentDisposition fileDetail,
        @QueryParam("separator") final String separator,
        @QueryParam("hasHeader") final String hasHeader) {

        try {
            final Set<CsvLine> set = ObjectBuilder
                .ofInputStream(uploadedInputStream)
                .ofCsv(separator, new Boolean(hasHeader))
                .stream()
                .collect(Collectors.toSet());
            
            return Response.status(200).entity(set).build();
        } catch (final IOException e) {
            e.printStackTrace();
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
