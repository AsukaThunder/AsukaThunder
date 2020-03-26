package com.ford.asukathunder.common.entity.area;

import com.ford.asukathunder.common.entity.school.School;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Area
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 2:23
 **/
@Entity
@Table(name = "area", indexes = {
        @Index(name = "idx1_area", columnList = "level"),
        @Index(name = "idx2_area", columnList = "sort")
})
@DynamicUpdate
@Getter
@Setter
public class Area implements Serializable{

    /**
     * 地域ID
     */
    @Id
    @Column(name = "area_id", updatable = false, unique = true)
    private Integer areaId;

    /**
     * 父地域信息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_area_id", referencedColumnName = "area_id", foreignKey = @ForeignKey(name = "fk1_area"))
    private Area parent;

    /**
     * 地域名称
     */
    @Column(name = "area_name", length = 30)
    private String areaName;

    /**
     * 短名称
     */
    @Column(name = "short_name", length = 30)
    private String shortName;

    /**
     * 经度
     */
    @Column(name = "lng")
    private String lng;

    /**
     * 纬度
     */
    @Column(name = "lat")
    private String lat;

    /**
     * 层级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 学校列表
     */
    @OneToMany(mappedBy = "area")
    private List<School> schoolList;

    /**
     * 子地域列表
     */
    @OneToMany(mappedBy = "parent")
    private List<Area> sonList;

    /**
     *  设置地区名称
     */
    public String combineAreaName() {
        if (this.parent == null) {
            return this.areaName;
        } else {
            return this.parent.getAreaName() + "-" + this.areaName;
        }
    }

}
