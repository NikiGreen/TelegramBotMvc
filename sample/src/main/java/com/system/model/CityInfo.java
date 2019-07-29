package com.system.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CityInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_info")
    private String cityInfo;

    public CityInfo() {
    }

    public CityInfo(String cityName, String cityInfo) {
        this.cityName = cityName;
        this.cityInfo = cityInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }
}
