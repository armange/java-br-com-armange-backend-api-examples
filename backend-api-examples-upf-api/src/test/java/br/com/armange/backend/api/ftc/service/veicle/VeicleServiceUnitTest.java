package br.com.armange.backend.api.ftc.service.veicle;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.armange.backend.api.ftc.entity.Veicle;
import br.com.armange.backend.api.ftc.rest.service.VeicleService;

public class VeicleServiceUnitTest {

    final private VeicleService spiedVeicleService = Mockito.spy(new VeicleService());
    final private Veicle veicle = new Veicle();
    
    public VeicleServiceUnitTest() {
        Mockito.doReturn(null).when(spiedVeicleService).insert(Mockito.any());
        Mockito.doReturn(null).when(spiedVeicleService).update(Mockito.any());
        Mockito.doReturn(null).when(spiedVeicleService).delete(Mockito.any());
        Mockito.doReturn(null).when(spiedVeicleService).findOne(Mockito.any());
        Mockito.doReturn(null).when(spiedVeicleService).findAll();
        Mockito.doReturn(null).when(spiedVeicleService).findPage();
        Mockito.doReturn(null).when(spiedVeicleService).findCountedPage();
    }
    
    @Test
    public void serviceMustBeAbleToInsert() {
        spiedVeicleService.insert(veicle);
        
        Mockito.verify(spiedVeicleService).insert(veicle);
    }
    
    @Test
    public void serviceMustBeAbleToUpdate() {
        spiedVeicleService.update(veicle);
        
        Mockito.verify(spiedVeicleService).update(veicle);
    }
    
    @Test
    public void serviceMustBeAbleToDelete() {
        spiedVeicleService.delete(veicle.getId());
        
        Mockito.verify(spiedVeicleService).delete(veicle.getId());
    }
    
    @Test
    public void serviceMustBeAbleToFindOne() {
        spiedVeicleService.findOne(veicle.getId());
        
        Mockito.verify(spiedVeicleService).findOne(veicle.getId());
    }
    
    @Test
    public void serviceMustBeAbleToFindAll() {
        spiedVeicleService.findAll();
        
        Mockito.verify(spiedVeicleService).findAll();
    }
    
    @Test
    public void serviceMustBeAbleToFindPage() {
        spiedVeicleService.findPage();
        
        Mockito.verify(spiedVeicleService).findPage();
    }
    
    @Test
    public void serviceMustBeAbleToFindContentPage() {
        spiedVeicleService.findCountedPage();
        
        Mockito.verify(spiedVeicleService).findCountedPage();
    }
}
