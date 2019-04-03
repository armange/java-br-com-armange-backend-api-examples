package br.com.armange.backend.api.ftc.entity;

import javax.persistence.Entity;

import br.com.armange.entity.BaseEntityGeneratedId;

@Entity
public class City extends BaseEntityGeneratedId<Long> {

    private Long IbgeId;
    private String uf;
    private String name;
    private Boolean capital;
    private Double latitude;
    private Double longitude;
    private String noAccents;
    private String microregion;
    private String mesoregion;
    
    public Long getIbgeId() {
        return IbgeId;
    }
    
    public void setIbgeId(final Long ibgeId) {
        IbgeId = ibgeId;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(final String uf) {
        this.uf = uf;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Boolean getCapital() {
        return capital;
    }
    
    public void setCapital(final Boolean capital) {
        this.capital = capital;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }
    
    public String getNoAccents() {
        return noAccents;
    }
    
    public void setNoAccents(final String noAccents) {
        this.noAccents = noAccents;
    }
    
    public String getMicroregion() {
        return microregion;
    }
    
    public void setMicroregion(final String microregion) {
        this.microregion = microregion;
    }
    
    public String getMesoregion() {
        return mesoregion;
    }
    
    public void setMesoregion(final String mesoregion) {
        this.mesoregion = mesoregion;
    }
}
