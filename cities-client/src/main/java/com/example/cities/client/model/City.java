package com.example.cities.client.model;

import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String county;

    private String stateCode;

    private String postalCode;

    private String latitude;

    private String longitude;

    public City() {
    }

    public City(String name, String stateCode, String postalCode) {
        this.name = name;
        this.stateCode = stateCode;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
