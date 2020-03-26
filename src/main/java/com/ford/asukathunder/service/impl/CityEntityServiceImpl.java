package com.ford.asukathunder.service.impl;

import com.ford.asukathunder.common.entity.jpa.city_country.CityEntity;
import com.ford.asukathunder.common.entity.jpa.city_country.CityNameOnly;
import com.ford.asukathunder.common.entity.jpa.city_country.CountryEntity;
import com.ford.asukathunder.common.entity.jpa.other.Statistic;
import com.ford.asukathunder.repository.CityEntityRepository;
import com.ford.asukathunder.service.CityEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Service
public class CityEntityServiceImpl implements CityEntityService {

    @Autowired
    CityEntityRepository cityEntityRepository;

    @PersistenceContext
    EntityManager entityManager;

    /**
     *   Specification 与 Sql 之间转换。
     *
     *  1. 查询全部 select * from city;
     *  2. 条件查询 select * from city where name='Kabul'
     *  3. 关联查询
     *  {
     *      1.内关联： select * from city c inner join country co on c.country_code = co.code where c.name='Kabul' and co.name='Afghanistan'
     *      2.左关联： select * from city c left join country co on c.country_code = co.code where c.name='Kabul' and co.name='Afghanistan'
     *      3.右关联：select * from city c right join country co on c.country_code = co.code where c.name='Kabul' and co.name='Afghanistan'
     *  }
     *  4. 子查询 select * from city where country_code = (select code from country where code='AFG')
     *  5. 返回部分字段 select name from city
     *  6. 通过jpa 定义查询查询部分字段
     *
     *
     *
     */
    // 1

    /**
     * select
     * cityentity0_.id as id1_0_,
     * cityentity0_.district as district2_0_,
     * cityentity0_.name as name3_0_,
     * cityentity0_.population as populati4_0_
     * from
     * city cityentity0_
     * where
     * 1=1
     *
     * @return
     */
    @Override
    public List<CityEntity> getCities() {
        Specification<CityEntity> spec = (root, criteriaQuery, criteriaBuilder) -> {
            /*Predicate predicate = criteriaBuilder.conjunction();
            return predicate;*/
            return null;
        };
        return cityEntityRepository.findAll(spec);
    }

    // 2

    /**
     * select
     * cityentity0_.id as id1_0_,
     * cityentity0_.district as district2_0_,
     * cityentity0_.name as name3_0_,
     * cityentity0_.population as populati4_0_
     * from
     * city cityentity0_
     * where
     * cityentity0_.name=?
     *
     * @param name
     * @return
     */
    @Override
    public CityEntity getCity(String name) {
        Specification<CityEntity> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), name);
            return predicate;
        };
        return cityEntityRepository.findOne(spec).get();
    }

    // 3

    /**
     * 如果不指定关联
     * select
     *         cityentity0_.id as id1_0_,
     *         cityentity0_.country_code as country_5_0_,
     *         cityentity0_.district as district2_0_,
     *         cityentity0_.name as name3_0_,
     *         cityentity0_.population as populati4_0_
     *     from
     *         city cityentity0_ cross
     *     join
     *         country countryent1_
     *     where
     *         cityentity0_.country_code=countryent1_.code
     *         and cityentity0_.name=?
     *         and countryent1_.name=?
     * 如果指定关联
     * select
     *         cityentity0_.id as id1_0_,
     *         cityentity0_.country_code as country_5_0_,
     *         cityentity0_.district as district2_0_,
     *         cityentity0_.name as name3_0_,
     *         cityentity0_.population as populati4_0_
     *     from
     *         city cityentity0_
     *     inner join
     *         country countryent1_
     *             on cityentity0_.country_code=countryent1_.code
     *     where
     *         cityentity0_.name=?
     *         and countryent1_.name=?
     */
    @Override
    public CityEntity getJoinCity(String cityName, String countryName) {
        Specification<CityEntity> spec = (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("name").as(String.class), cityName);
            // 关联Country表
            root.join("countryEntity", JoinType.INNER); //JoinType.LEFT; JoinType.RIGHT
            // 关联表字段过滤
            predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("countryEntity").get("name").as(String.class), countryName));
            return predicate;
        };
        return cityEntityRepository.findOne(spec).get();
    }

    //4

    /**
     * select * from city where name = (select localName from country where code=?)
     */
    @Override
    public CityEntity getSubQueryCity(String countryCode){
        Specification<CityEntity> spec = (root, criteriaQuery, criteriaBuilder) -> {

            Root<CountryEntity> countryEntityRoot = criteriaQuery.from(CountryEntity.class);
            Predicate predicate = criteriaBuilder.equal(countryEntityRoot.get("code").as(String.class), countryCode);
            Subquery<CountryEntity> subquery = criteriaQuery.subquery(CountryEntity.class);
            subquery.select(countryEntityRoot.get("localName")); // 获取需要查询的某个值;
            subquery.where(predicate);
            // select localName from countryEntity where code = ?;
            // 进入父查询
            Predicate pPredicate =  criteriaBuilder.equal(root.get("name").as(String.class),subquery);

            return pPredicate;
        };
        return cityEntityRepository.findOne(spec).get();
    }

    /**
     * select
     *         cityentity0_.name as col_0_0_
     *     from
     *         city cityentity0_
     * @return
     */
    @Override
    public CityNameOnly getCityNameOnly(){
        // 查寻结果返回自定义对象
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityNameOnly> criteriaQuery = criteriaBuilder.createQuery(CityNameOnly.class);

        Root<CityEntity> cityEntityRoot = criteriaQuery.from(CityEntity.class);
        // 这个select只是字符串不能转成对象，如果要转成对象用以下multiselect
        //criteriaQuery.select(cityEntityRoot.get("name"));
        // 如果字段使用,自定义类必须要有参数构造函数
        criteriaQuery.multiselect(cityEntityRoot.get("name"));

        TypedQuery<CityNameOnly> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    /**
     * select
     *         cityentity0_.id as col_0_0_,
     *         count(cityentity0_.name) as col_1_0_
     *     from
     *         city cityentity0_
     *     group by
     *         cityentity0_.id
     *     having
     *         count(cityentity0_.name)>=0
     * @return
     */
    @Override
    public Statistic getStatistic(){
        // 查寻结果返回自定义对象,statistic为自定义对象
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Statistic> criteriaQuery = criteriaBuilder.createQuery(Statistic.class);

        Root<CityEntity> cityEntityRoot = criteriaQuery.from(CityEntity.class);
        Path<Integer> id = cityEntityRoot.get("id");
        Path<String> name = cityEntityRoot.get("name");
        // 查询需要的字段
        criteriaQuery.multiselect(id,criteriaBuilder.count(name));

        // 根据id分组
        criteriaQuery.groupBy(id);
        // 筛选改组name 统计大于0的数据
        criteriaQuery.having(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.count(name),0L));
        TypedQuery<Statistic> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

}
