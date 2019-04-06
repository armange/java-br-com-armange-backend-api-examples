package br.com.armange.backend.api.ftc.service.color;

import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import br.com.armange.backend.api.ftc.entity.Color;
import br.com.armange.backend.api.ftc.rest.service.ColorService;
import br.com.armange.dao.CountedPage;
import br.com.armange.dao.Page;

public class ColorServiceIntegrationTest {

    private static final String DESCRIPTION = "description";
    private static final String ID = "id";
    
    private final ColorService spiedColorService = new ColorService();
    private final Color color = new Color();
    
    public ColorServiceIntegrationTest() {
        color.setDescription("Dourado");
    }
    
    @Test
    public void serviceMustBeAbleToInsert() {
        final Response response = spiedColorService.insert(color);

        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        final Color color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
    }

    @Test
    public void serviceMustBeAbleToUpdate() {
        color.setId(null);
        
        Response response = spiedColorService.insert(color);

        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        Color color = (Color) entity;
        
        final String prataColor = "Prata";
        color.setDescription(prataColor);
        
        spiedColorService.update(color);
        response = spiedColorService.findOne(color.getId());
        
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, 
                Matchers.allOf(
                        Matchers.hasProperty(ID, Matchers.notNullValue()),
                        Matchers.hasProperty(DESCRIPTION, Matchers.equalTo(prataColor))
                        )
                );
    }
    
    @Test
    public void serviceMustBeAbleToDelete() {
        color.setId(null);
        
        Response response = spiedColorService.insert(color);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        Color color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        spiedColorService.delete(color.getId());
        
        response = spiedColorService.findOne(color.getId());
        Assertions.assertThat(response.getEntity()).isNull();
    }
    
    @Test
    public void serviceMustBeAbleToFindOne() {
        Response response = spiedColorService.insert(color);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        Color color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedColorService.findOne(color.getId());
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.equalTo(color.getId())));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindAll() {
        Response response = spiedColorService.insert(color);
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        Color color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedColorService.insert(new Color());
        entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Color.class);
        color = (Color) entity;
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedColorService.findAll();
        List<Color> colors = (List<Color>) response.getEntity();
        
        Assertions.assertThat(colors).isNotNull();
        Assert.assertThat(colors, Matchers.hasSize(Matchers.greaterThanOrEqualTo(2)));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindPage() {
        Response response = spiedColorService.insert(color);
        Color color = (Color) response.getEntity();
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedColorService.findPage();
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(Page.class);
        
        Page<Color> colorPage = (Page<Color>) entity;
        Assertions.assertThat(colorPage).isNotNull();
        Assertions.assertThat(colorPage).isNotInstanceOf(CountedPage.class);
        
        final List<Color> rows = colorPage.getRows();
        Assertions.assertThat(rows).isNotNull();
        
        Assert.assertThat(rows, Matchers.hasItem(
                Matchers.hasProperty(ID, Matchers.equalTo(color.getId()))));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void serviceMustBeAbleToFindContentPage() {
        Response response = spiedColorService.insert(color);
        Color color = (Color) response.getEntity();
        
        Assertions.assertThat(color).isNotNull();
        Assert.assertThat(color, Matchers.hasProperty(ID, Matchers.notNullValue()));
        
        response = spiedColorService.findCountedPage();
        Object entity = response.getEntity();
        Assertions.assertThat(entity).isInstanceOf(CountedPage.class);
        
        CountedPage<Color> colorPage = (CountedPage<Color>) entity;
        Assertions.assertThat(colorPage).isNotNull();
        Assertions.assertThat(colorPage.getPageCount()).isNotNull();
        
        final List<Color> rows = colorPage.getRows();
        Assertions.assertThat(rows).isNotNull();
        
        Assert.assertThat(rows, Matchers.hasItem(
                Matchers.hasProperty(ID, Matchers.equalTo(color.getId()))));
    }
}
