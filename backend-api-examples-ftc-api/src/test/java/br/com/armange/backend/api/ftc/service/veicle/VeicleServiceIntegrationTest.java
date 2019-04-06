package br.com.armange.backend.api.ftc.service.veicle;

import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.armange.backend.api.ftc.entity.Veicle;
import br.com.armange.backend.api.ftc.rest.service.VeicleService;
import br.com.armange.dao.CountedPage;
import br.com.armange.dao.Page;

public class VeicleServiceIntegrationTest {

    private static final String MANUFACTURE_YEAR = "manufactureYear";
    private static final String ID = "id";
    
    private final VeicleService spiedVeicleService = new VeicleService();
    private final Veicle veicle = new Veicle();
    
    public VeicleServiceIntegrationTest() {
        veicle.setManufactureYear((short) 2019);
    }
    
    @Test
    public void serviceMustBeAbleToInsert() {
        final Response response = spiedVeicleService.insert(veicle);

        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        final Veicle veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }

    @Test
    public void serviceMustBeAbleToUpdate() {
        veicle.setId(null);
        
        Response response = spiedVeicleService.insert(veicle);

        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        Veicle veicle = (Veicle) entity;
        
        final short year2018 = 2018;
        veicle.setManufactureYear(year2018);
        
        spiedVeicleService.update(veicle);
        response = spiedVeicleService.findOne(veicle.getId());
        
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, 
                Matchers.allOf(
                        Matchers.hasProperty(ID, Matchers.notNullValue()),
                        Matchers.hasProperty(MANUFACTURE_YEAR, Matchers.equalTo(year2018))
                        )
                );
    }
    
    @Test
    public void serviceMustBeAbleToDelete() {
        veicle.setId(null);
        
        Response response = spiedVeicleService.insert(veicle);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        Veicle veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        spiedVeicleService.delete(veicle.getId());
        
        response = spiedVeicleService.findOne(veicle.getId());
        Assertions.assertThat(response.getEntity()).isNull();
    }
    
    @Test
    public void serviceMustBeAbleToFindOne() {
        Response response = spiedVeicleService.insert(veicle);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        Veicle veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedVeicleService.findOne(veicle.getId());
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.equalTo(veicle.getId())));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindAll() {
        Response response = spiedVeicleService.insert(veicle);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        Veicle veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedVeicleService.insert(new Veicle());
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Veicle.class);
        veicle = (Veicle) entity;
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedVeicleService.findAll();
        List<Veicle> veicles = (List<Veicle>) response.getEntity();
        
        Assertions.assertThat(veicles).isNotNull();
        Assert.assertThat(veicles, Matchers.hasSize(Matchers.greaterThanOrEqualTo(2)));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindPage() {
        Response response = spiedVeicleService.insert(veicle);
        Veicle veicle = (Veicle) response.getEntity();
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedVeicleService.findPage();
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Page.class);
        
        Page<Veicle> veiclePage = (Page<Veicle>) entity;
        Assertions.assertThat(veiclePage).isNotNull();
        Assertions.assertThat(veiclePage).isNotInstanceOf(CountedPage.class);
        
        final List<Veicle> rows = veiclePage.getRows();
        Assertions.assertThat(rows).isNotNull();
        
        Assert.assertThat(rows, Matchers.hasItem(
                Matchers.hasProperty(ID, Matchers.equalTo(veicle.getId()))));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindContentPage() {
        Response response = spiedVeicleService.insert(veicle);
        Veicle veicle = (Veicle) response.getEntity();
        
        Assertions.assertThat(veicle).isNotNull();
        Assert.assertThat(veicle, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedVeicleService.findCountedPage();
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(CountedPage.class);
        
        CountedPage<Veicle> veiclePage = (CountedPage<Veicle>) entity;
        Assertions.assertThat(veiclePage).isNotNull();
        Assertions.assertThat(veiclePage.getPageCount()).isNotNull();
        
        final List<Veicle> rows = veiclePage.getRows();
        Assertions.assertThat(rows).isNotNull();
        
        Assert.assertThat(rows, Matchers.hasItem(
                Matchers.hasProperty(ID, Matchers.equalTo(veicle.getId()))));
    }
}
