package br.com.armange.backend.api.ftc.resource.veicle;

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

import br.com.armange.backend.api.ftc.entity.Veicle;
import br.com.armange.backend.api.ftc.rest.resource.VeicleResource;
import br.com.armange.dao.CountedPageImpl;
import br.com.armange.dao.PageImpl;

public class VeicleResourceIntegrationTest extends JerseyTest {

    private static final String ID = "id";
    
    private static final String YEAR = "manufactureYear";

    private static final String VEICLE_RESOURCE = "veicle";
    
    private ResourceConfig resourceConfig;
    
    @Override
    public Application configure() {
        resourceConfig = new ResourceConfig(
            VeicleResource.class);
        
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
        final Entity<Veicle> bodyEntity = Entity.entity(new Veicle(), MediaType.APPLICATION_JSON);
        final Response response = target(VEICLE_RESOURCE).request().post(bodyEntity);
        
        Assert.assertEquals(201, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final Veicle entity = response.readEntity(Veicle.class);
        
        Assert.assertNotNull(entity);
        Assert.assertThat(entity, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }
    
    @Test
    public void theResourceMustBeAbleToUpdate() {
        Response response = insertEntity();
        
        //Update entity
        Veicle veicle = response.readEntity(Veicle.class);
        
        Assert.assertNotNull(veicle);
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        final Short year = 2018;
        veicle.setManufactureYear(year);
        
        Entity<Veicle> bodyEntity = Entity.entity(veicle, MediaType.APPLICATION_JSON);
        response = target(VEICLE_RESOURCE).request().put(bodyEntity);
        
        Assert.assertEquals(204, response.getStatus());
        Assert.assertFalse(response.hasEntity());
        
        // Find entity
        response = target(VEICLE_RESOURCE).path(veicle.getId().toString()).request().get();
        Assert.assertTrue(response.hasEntity());
        
        veicle = response.readEntity(Veicle.class);
        
        Assert.assertNotNull(veicle);
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        Assert.assertThat(veicle, Matchers.hasProperty(YEAR, Matchers.equalTo(year)));
    }
    
    @Test
    public void theResourceMustBeAbleToFindOne() {
        Response response = insertEntity();
        Veicle veicle = response.readEntity(Veicle.class);
        
        // Find entity
        response = target(VEICLE_RESOURCE).path(veicle.getId().toString()).request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        veicle = response.readEntity(Veicle.class);
        
        Assert.assertNotNull(veicle);
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }
    
    @Test
    public void theResourceMustBeAbleToFindAll() {
        insertEntity();
        insertEntity();
        
        // Find entity
        Response response = target(VEICLE_RESOURCE).request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<List<Veicle>> listType = new GenericType<List<Veicle>>() {};
        final List<Veicle> veicles = response.readEntity(listType);
        
        Assert.assertNotNull(veicles);
        Assert.assertThat(veicles, Matchers.hasSize(Matchers.greaterThanOrEqualTo(2)));
    }
    
    @Test
    public void theResourceMustBeAbleToFindPage() {
        insertEntity();
        
        // Find entity
        Response response = target(VEICLE_RESOURCE).path("page").request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<PageImpl<Veicle>> listType = new GenericType<PageImpl<Veicle>>() {};
        final PageImpl<Veicle> veicles = response.readEntity(listType);
        
        Assert.assertNotNull(veicles);
        Assert.assertNotNull(veicles.getRows());
        Assert.assertThat(veicles.getRows(), Matchers.notNullValue());
    }
    
    @Test
    public void theResourceMustBeAbleToFindCountedPage() {
        insertEntity();
        
        // Find entity
        Response response = target(VEICLE_RESOURCE).path("counted-page").request().get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        final GenericType<CountedPageImpl<Veicle>> listType = new GenericType<CountedPageImpl<Veicle>>() {};
        final CountedPageImpl<Veicle> veicles = response.readEntity(listType);
        
        Assert.assertNotNull(veicles);
        Assert.assertNotNull(veicles.getRows());
        Assert.assertThat(veicles.getRows(), Matchers.notNullValue());
    }

    private Response insertEntity() {
        final Entity<Veicle> bodyEntity = Entity.entity(new Veicle(), MediaType.APPLICATION_JSON);
        final Response response = target(VEICLE_RESOURCE).request().post(bodyEntity);
        
        Assert.assertEquals(201, response.getStatus());
        Assert.assertTrue(response.hasEntity());
        
        return response;
    }
}
