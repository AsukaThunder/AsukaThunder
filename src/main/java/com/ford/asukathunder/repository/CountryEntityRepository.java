package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.city_country.CountryEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @ClassName: CountryEntityRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/20 上午 11:53
 **/
public interface CountryEntityRepository extends JpaRepositoryImplementation<CountryEntity,String> {
}
