package br.com.armange.backend.api.ftc.rest.service;

import br.com.armange.backend.api.ftc.entity.Color;
import br.com.armange.dao.Dao;
import br.com.armange.dao.DaoImpl;
import br.com.armange.dao.OrmServerImpl;
import br.com.armange.rest.service.ServiceDelete;
import br.com.armange.rest.service.ServiceFindAll;
import br.com.armange.rest.service.ServiceFindCountedPage;
import br.com.armange.rest.service.ServiceFindOne;
import br.com.armange.rest.service.ServiceFindPage;
import br.com.armange.rest.service.ServiceInsert;
import br.com.armange.rest.service.ServiceUpdate;

public class ColorService implements 
    ServiceDelete<Integer, Color>, 
    ServiceFindAll<Integer, Color>, 
    ServiceFindCountedPage<Integer, Color>, 
    ServiceFindOne<Integer, Color>, 
    ServiceFindPage<Integer, Color>, 
    ServiceInsert<Integer, Color>, 
    ServiceUpdate<Integer, Color> {
    
    @Override
    public Dao<Integer, Color> getDao() {
        return new DaoImpl<>(new OrmServerImpl("h2"), Color.class);
    }
}
