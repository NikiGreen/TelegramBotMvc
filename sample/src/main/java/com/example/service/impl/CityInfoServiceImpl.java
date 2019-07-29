package com.example.service.impl;

import com.example.model.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.CityInfoRepository;
import com.example.service.CityInfoService;

import java.util.List;

@Service
public class CityInfoServiceImpl implements CityInfoService {


    private final CityInfoRepository cityInfoRepository;

    @Autowired
    public CityInfoServiceImpl(CityInfoRepository cityInfoRepository) {
        this.cityInfoRepository = cityInfoRepository;
    }

    @Override
    public CityInfo addCityInfo(CityInfo cityInfo) {
        CityInfo savedCityInfo = cityInfoRepository.saveAndFlush(cityInfo);

        return savedCityInfo;
    }

    @Override
    public void deleteByName(String cityName) {
        cityInfoRepository.deleteAllByCityName(cityName);
    }

    @Override
    public List<CityInfo> getByName(String name) {
        return cityInfoRepository.findByCityName(name);
    }

    @Override
    public CityInfo editCityInfo(CityInfo cityInfo) {
        return cityInfoRepository.saveAndFlush(cityInfo);
    }

    @Override
    public List<CityInfo> getAll() {
        return cityInfoRepository.findAll();
    }
}
