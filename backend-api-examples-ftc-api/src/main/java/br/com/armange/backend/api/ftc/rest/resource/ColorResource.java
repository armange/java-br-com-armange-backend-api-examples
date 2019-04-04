package br.com.armange.backend.api.ftc.rest.resource;

import br.com.armange.backend.api.ftc.entity.Color;
import br.com.armange.backend.api.ftc.rest.service.ColorService;
import br.com.armange.rest.resource.ResourceFindAll;
import br.com.armange.rest.resource.ResourceFindCountedPage;
import br.com.armange.rest.resource.ResourceFindOne;
import br.com.armange.rest.resource.ResourceFindPage;
import br.com.armange.rest.resource.ResourceInsert;
import br.com.armange.rest.resource.ResourceUpdate;

public class ColorResource implements 
    ResourceFindAll<Integer, Color, ColorService>, 
    ResourceFindCountedPage<Integer, Color, ColorService>, 
    ResourceFindOne<Integer, Color, ColorService>, 
    ResourceFindPage<Integer, Color, ColorService>, 
    ResourceInsert<Integer, Color, ColorService>, 
    ResourceUpdate<Integer, Color, ColorService> {
    
    @Override
    public ColorService getService() {
        return new ColorService();
    }
}
