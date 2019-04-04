package br.com.armange.backend.api.ftc.service.color;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.armange.backend.api.ftc.entity.Color;
import br.com.armange.backend.api.ftc.rest.service.ColorService;

public class ColorServiceUnitTest {

    final private ColorService spiedColorService = Mockito.spy(new ColorService());
    final private Color color = new Color();
    
    public ColorServiceUnitTest() {
        Mockito.doReturn(null).when(spiedColorService).insert(Mockito.any());
        Mockito.doReturn(null).when(spiedColorService).update(Mockito.any());
        Mockito.doReturn(null).when(spiedColorService).delete(Mockito.any());
        Mockito.doReturn(null).when(spiedColorService).findOne(Mockito.any());
        Mockito.doReturn(null).when(spiedColorService).findAll();
        Mockito.doReturn(null).when(spiedColorService).findPage();
        Mockito.doReturn(null).when(spiedColorService).findCountedPage();
    }
    
    @Test
    public void serviceMustBeAbleToInsert() {
        spiedColorService.insert(color);
        
        Mockito.verify(spiedColorService).insert(color);
    }
    
    @Test
    public void serviceMustBeAbleToUpdate() {
        spiedColorService.update(color);
        
        Mockito.verify(spiedColorService).update(color);
    }
    
    @Test
    public void serviceMustBeAbleToDelete() {
        spiedColorService.delete(color);
        
        Mockito.verify(spiedColorService).delete(color);
    }
    
    @Test
    public void serviceMustBeAbleToFindOne() {
        spiedColorService.findOne(color.getId());
        
        Mockito.verify(spiedColorService).findOne(color.getId());
    }
    
    @Test
    public void serviceMustBeAbleToFindAll() {
        spiedColorService.findAll();
        
        Mockito.verify(spiedColorService).findAll();
    }
    
    @Test
    public void serviceMustBeAbleToFindPage() {
        spiedColorService.findPage();
        
        Mockito.verify(spiedColorService).findPage();
    }
    
    @Test
    public void serviceMustBeAbleToFindContentPage() {
        spiedColorService.findCountedPage();
        
        Mockito.verify(spiedColorService).findCountedPage();
    }
}
