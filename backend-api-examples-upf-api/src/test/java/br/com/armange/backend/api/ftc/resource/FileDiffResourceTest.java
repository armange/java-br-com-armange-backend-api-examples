package br.com.armange.backend.api.ftc.resource;

import java.io.File;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.armange.backend.api.upf.rest.resource.FileDiffResource;
import br.com.armange.backend.api.upf.rest.service.FileSystemService;
import br.com.armange.backend.api.upf.server.configuration.PropertyKeyHandler;

public class FileDiffResourceTest extends JerseyTest {
    private static final String PATH_ID_DIFF = "id/diff";
    private static final String PATH_ID_TO = "id/to";
    private static final String PATH_ID_FROM = "id/from";
    private static final String INPUT_KEY = "text-file";
    private static final String RESOURCE = "diff";

private ResourceConfig resourceConfig;
    
    @Override
    public Application configure() {
        resourceConfig = new ResourceConfig(
            FileDiffResource.class,
            MultiPartFeature.class);
        
        resourceConfig.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                enable(TestProperties.LOG_TRAFFIC);
                
                enable(TestProperties.DUMP_ENTITY);
            }
        });
        
        return resourceConfig;
    }
    
    @Before
    public void beforeTest() {
        replaceBaseDirectorySystemProperty();
    }
    
    private void replaceBaseDirectorySystemProperty() {
        System.setProperty(
                StringUtils.join(
                        br.com.armange.backend.api.upf.server.configuration.ServerProperties.PREFIX,
                        PropertyKeyHandler.PROPERTY_SEPARATOR,
                        br.com.armange.backend.api.upf.server.configuration.ServerProperties.BASE_DIRECTORY), 
                getBaseDirectory());
    }
    
    private String getBaseDirectory() {
        final String path = getClass().getResource("/id/").getPath();
        return path.substring(0, path.length()-3);
    }

    @Test
    public void addedNewLineMustBeReported() {
        try {
            Response response = sendFile(getOriginalFileNonBlank(), PATH_ID_FROM);
            
            Assert.assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
            
            response = null;
            
            response = sendFile(getRevisedAdded(), PATH_ID_TO);
            
            Assert.assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
            
            response = target(RESOURCE).path(PATH_ID_DIFF).request().get();
            
            Assert.assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
            
            Assert.assertEquals(
                    StringUtils.join(
                            "= Linha 01\n\n", 
                            "= Linha 02\n\n", 
                            "+ Linha 03\n"
                            ),
                    response.readEntity(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            
            Assert.fail(e.getMessage());
        }
    }
    
    private Response sendFile(final File file, final String resourcePath) throws Exception {
        try (final FormDataMultiPart formDataMultiPart = new FormDataMultiPart();) {
            final FileDataBodyPart filePart = new FileDataBodyPart(INPUT_KEY, file);
            final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
            final Response response = target(RESOURCE)
                    .register(MultiPartFeature.class)
                    .path(resourcePath)
                    .request()
                    .post(Entity.entity(multipart, multipart.getMediaType()));
            
            Assert.assertEquals(201, response.getStatus());
            Assert.assertFalse(response.hasEntity());
            
            return response;
        } catch (final Exception e) {
            throw e;
        }
    }
    
    private File getOriginalFileNonBlank() {
        return FileUtils.getFile(FileSystemService.getBaseDirectory(), "id/original.txt");
    }
    
    private File getRevisedAdded() {
        return FileUtils.getFile(FileSystemService.getBaseDirectory(), "id/revised-added.txt");
    }
}
