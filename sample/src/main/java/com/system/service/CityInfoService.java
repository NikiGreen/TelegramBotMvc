package com.system.service;

import com.system.model.CityInfo;

import java.util.List;

public interface CityInfoService {
    CityInfo addCityInfo(CityInfo cityName);

    void deleteByName(String cityName);

    List<CityInfo> getByName(String name);

    CityInfo editCityInfo(CityInfo cityInfo);

    List<CityInfo> getAll();
}
