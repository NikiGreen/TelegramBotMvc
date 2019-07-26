package com.example.service;

import com.example.model.CityInfo;

import java.util.List;

public interface CityInfoService {
    CityInfo addCityInfo(CityInfo bank);
    void delete(long id);
    List<CityInfo> getByName(String name);
    CityInfo editCityInfo(CityInfo bank);
    List<CityInfo> getAll();
}
