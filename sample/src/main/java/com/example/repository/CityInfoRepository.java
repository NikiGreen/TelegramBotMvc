package com.example.repository;


import com.example.model.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo,Long> {
    List<CityInfo> findByCityName(String cityName);

    void deleteAllByCityName(String cityName);
}
