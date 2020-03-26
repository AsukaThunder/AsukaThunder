package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.city_country.CityEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @ClassName: CityEntityRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/20 上午 11:53
 **/
public interface CityEntityRepository extends JpaRepositoryImplementation<CityEntity,String> {
}
