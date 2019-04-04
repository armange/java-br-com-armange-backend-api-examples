package br.com.armange.backend.api.ftc.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.armange.entity.Activable;
import br.com.armange.entity.AuditableEntityGeneratedId;

/*
 * The primary key was set to Integer because this project is an example of 
 * CRUD and does not have to persist large amounts of data. 
 * */
@Entity
public class Veicle extends AuditableEntityGeneratedId<Integer> implements Activable {

    private Short modelYear;
    private Short manufactureYear;
    private boolean active;
    
    @ManyToOne
    @JoinColumn(name="color_id")
    private Color color;
    
    public Veicle() {
        active = true;
    }
    
    public Short getModelYear() {
        return modelYear;
    }

    public void setModelYear(final Short modelYear) {
        this.modelYear = modelYear;
    }

    public Short getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(final Short manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    @Override
    public boolean isActive() {
        return active;
    }
    
    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }
}
