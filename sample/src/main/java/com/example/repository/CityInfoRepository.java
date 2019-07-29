package com.example.repository;


import com.example.model.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo,Long> {
    List<CityInfo> findByCityName(String cityName);

    @Transactional
    void deleteAllByCityName(String cityName);
}
