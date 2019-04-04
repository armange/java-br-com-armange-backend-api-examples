package br.com.armange.backend.api.ftc.rest.service;

import br.com.armange.backend.api.ftc.entity.Veicle;
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

public class VeicleService implements 
    ServiceDelete<Integer, Veicle>, 
    ServiceFindAll<Integer, Veicle>, 
    ServiceFindCountedPage<Integer, Veicle>, 
    ServiceFindOne<Integer, Veicle>, 
    ServiceFindPage<Integer, Veicle>, 
    ServiceInsert<Integer, Veicle>, 
    ServiceUpdate<Integer, Veicle> {

    @Override
    public Dao<Integer, Veicle> getDao() {
        return new DaoImpl<>(new OrmServerImpl("h2"), Veicle.class);
    }
}
