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

import br.com.armange.backend.api.upf.rest.service.FileDiffService;
import br.com.armange.backend.api.upf.rest.service.FileSystemService;

@Path("/diff")
public class FileDiffResource extends AbstractFileResource {
    private static final String INPUT_KEY = "text-file";
    private static final String ID_PARAM_PATH = "id";

    @POST
    @Path("/{id}/from")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fromFile(
        @FormDataParam(INPUT_KEY) final InputStream uploadedInputStream,
        @FormDataParam(INPUT_KEY) final FormDataContentDisposition fileDetail,
        @PathParam(ID_PARAM_PATH) final String id) {
        try {
            FileUtils.copyInputStreamToFile(
                    uploadedInputStream, 
                    buildDestination(id, FileSystemService.FILE_ON_LEFT_NAME));
            
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
            FileUtils.copyInputStreamToFile(
                    uploadedInputStream, 
                    buildDestination(id, FileSystemService.FILE_ON_RIGHT_NAME));
            
            return Response.status(Status.CREATED).build();
        } catch (final IOException e) {
            e.printStackTrace();
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Path("/{id}/diff")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response diffFile(@PathParam(ID_PARAM_PATH) final String id) {
        try {
            final String diff = FileDiffService
                    .fromId(id)
                    .readFilesAndGetDiff();
            
            return Response
                    .status(diff == null ? Status.NO_CONTENT : Status.OK)
                    .entity(diff)
                    .build();
        } catch (final Exception e) {
            e.printStackTrace();
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
