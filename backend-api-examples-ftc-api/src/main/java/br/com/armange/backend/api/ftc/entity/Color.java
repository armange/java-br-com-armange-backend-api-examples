package br.com.armange.backend.api.ftc.entity;

import javax.persistence.Entity;

import br.com.armange.entity.BaseEntityGeneratedId;

/*
 * The primary key was set to Integer because this project is an example of 
 * CRUD and does not have to persist large amounts of data. 
 * */
@Entity
public class Color extends BaseEntityGeneratedId<Integer> {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
