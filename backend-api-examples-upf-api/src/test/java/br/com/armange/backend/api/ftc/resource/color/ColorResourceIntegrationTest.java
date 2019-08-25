package br.com.armange.backend.api.ftc.resource.color;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.armange.backend.api.ftc.entity.Color;
import br.com.armange.backend.api.ftc.rest.resource.ColorResource;
import br.com.armange.dao.CountedPageImpl;
import br.com.armange.dao.PageImpl;

public class ColorResourceIntegrationTest extends JerseyTest {

    private static final String ID = "id";
    
    private static final String DESCRIPTION = "description";

    private static final String COLOR_RESOURCE = "color";
    
    private ResourceConfig resourceConfig;
    
    @Override
    public Application configure() {
        resourceConfig = new ResourceConfig(
            ColorResource.class);
        
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
    
    @Test
    public void theResourceMustBeAbleToInsert() {
        final Entity<Color> bodyEntity = Entity.entity(new Color(), MediaType.APPLICATION_JSON);
        final Response response = target(COLOR_RESOURCE).request().post(bodyEntity);
        
        Assert.assertEquals(201, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final Color entity = response.readEntity(Color.class);
        
        Assert.assertNotNull(entity);
        Assert.assertThat(entity, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }
    
    @Test
    public void theResourceMustBeAbleToUpdate() {
        Response response = insertEntity();
        
        //Update entity
        Color color = response.readEntity(Color.class);
        
        Assert.assertNotNull(color);
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        final String description = "Prata";
        color.setDescription(description);
        
        Entity<Color> bodyEntity = Entity.entity(color, MediaType.APPLICATION_JSON);
        response = target(COLOR_RESOURCE).request().put(bodyEntity);
        
        Assert.assertEquals(204, response.getStatus());
        Assert.assertFalse(response.hasEntity());
        
        // Find entity
        response = target(COLOR_RESOURCE).path(color.getId().toString()).request().get();
        Assert.assertTrue(response.hasEntity());
        
        color = response.readEntity(Color.class);
        
        Assert.assertNotNull(color);
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        Assert.assertThat(color, Matchers.hasProperty(DESCRIPTION, Matchers.equalTo(description)));
    }
    
    @Test
    public void theResourceMustBeAbleToDelte() {
        //Insert entity
        Response response = insertEntity();
        
        //Delete entity
        Color color = response.readEntity(Color.class);
        response = target(COLOR_RESOURCE).path(color.getId().toString()).request().delete();
        
        Assert.assertEquals(204, response.getStatus());
        Assert.assertFalse(response.hasEntity());
        
        // Find entity
        response = target(COLOR_RESOURCE).path(color.getId().toString()).request().get();
        Assert.assertFalse(response.hasEntity());
    }
    
    @Test
    public void theResourceMustBeAbleToFindOne() {
        Response response = insertEntity();
        Color color = response.readEntity(Color.class);
        
        // Find entity
        response = target(COLOR_RESOURCE).path(color.getId().toString()).request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        color = response.readEntity(Color.class);
        
        Assert.assertNotNull(color);
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }
    
    @Test
    public void theResourceMustBeAbleToFindAll() {
        insertEntity();
        insertEntity();
        
        // Find entity
        Response response = target(COLOR_RESOURCE).request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<List<Color>> listType = new GenericType<List<Color>>() {};
        final List<Color> colors = response.readEntity(listType);
        
        Assert.assertNotNull(colors);
        Assert.assertThat(colors, Matchers.hasSize(Matchers.greaterThanOrEqualTo(2)));
    }
    
    @Test
    public void theResourceMustBeAbleToFindPage() {
        insertEntity();
        
        // Find entity
        Response response = target(COLOR_RESOURCE).path("page").request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<PageImpl<Color>> listType = new GenericType<PageImpl<Color>>() {};
        final PageImpl<Color> colors = response.readEntity(listType);
        
        Assert.assertNotNull(colors);
        Assert.assertNotNull(colors.getRows());
        Assert.assertThat(colors.getRows(), Matchers.notNullValue());
    }
    
    @Test
    public void theResourceMustBeAbleToFindCountedPage() {
        insertEntity();
        
        // Find entity
        Response response = target(COLOR_RESOURCE).path("counted-page").request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<CountedPageImpl<Color>> listType = new GenericType<CountedPageImpl<Color>>() {};
        final CountedPageImpl<Color> colors = response.readEntity(listType);
        
        Assert.assertNotNull(colors);
        Assert.assertNotNull(colors.getRows());
        Assert.assertThat(colors.getRows(), Matchers.notNullValue());
    }

    private Response insertEntity() {
        final Entity<Color> bodyEntity = Entity.entity(new Color(), MediaType.APPLICATION_JSON);
        final Response response = target(COLOR_RESOURCE).request().post(bodyEntity);
        
        Assert.assertEquals(201, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        return response;
    }
}
