package com.ford.asukathunder.service;

import com.ford.asukathunder.common.entity.jpa.city_country.CityEntity;
import com.ford.asukathunder.common.entity.jpa.city_country.CityNameOnly;
import com.ford.asukathunder.common.entity.jpa.other.Statistic;

import java.util.List;

/**
 * @ClassName: CityEntityService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/20 上午 11:55
 **/
public interface CityEntityService {
    List<CityEntity> getCities();
    CityEntity getCity(String name);
    CityEntity getJoinCity(String cityName, String countryName);
    CityEntity getSubQueryCity(String countryCode);
    Statistic getStatistic();
    CityNameOnly getCityNameOnly();
}
