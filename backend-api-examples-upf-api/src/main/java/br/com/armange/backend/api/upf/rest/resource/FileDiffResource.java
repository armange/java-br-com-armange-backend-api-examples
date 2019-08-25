package br.com.armange.backend.api.upf.rest.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/diff")
public class FileDiffResource extends AbstractFileResource {
    private static final String INPUT_KEY = "text-file";
    private static final String ID_PARAM_PATH = "ID";
    private static final String FROM_FILE_PREFIX = "from-";
    private static final String TO_FILE_PREFIX = "to-";

    @POST
    @Path("/{id}/from")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fromFile(
        @FormDataParam(INPUT_KEY) final InputStream uploadedInputStream,
        @FormDataParam(INPUT_KEY) final FormDataContentDisposition fileDetail,
        @PathParam(ID_PARAM_PATH) final String id) {
        try {
            FileUtils.copyInputStreamToFile(uploadedInputStream, buildDestination(id, FROM_FILE_PREFIX, fileDetail));
            
            return Response.status(Status.CREATED).build();
        } catch (final IOException e) {
            e.printStackTrace();
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Path("/{id}/to")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response toFile(
        @FormDataParam(INPUT_KEY) final InputStream uploadedInputStream,
        @FormDataParam(INPUT_KEY) final FormDataContentDisposition fileDetail,
        @PathParam(ID_PARAM_PATH) final String id) {
        try {
            FileUtils.copyInputStreamToFile(uploadedInputStream, buildDestination(id, TO_FILE_PREFIX, fileDetail));
            
            return Response.status(Status.CREATED).build();
        } catch (final IOException e) {
            e.printStackTrace();
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
