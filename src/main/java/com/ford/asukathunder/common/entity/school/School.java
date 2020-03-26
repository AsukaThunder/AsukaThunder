package com.ford.asukathunder.common.entity.school;

import com.ford.asukathunder.common.entity.area.Area;
import com.ford.asukathunder.common.entity.base.BaseEntity;
import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName: School
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 2:11
 **/
@Entity
@Table(name = "school")
@DynamicUpdate
@Setter
@Getter
public class School extends BaseEntity {

    @Override
    public void setEntityId(SnowflakeIdWorker snowflakeIdWorker) {
        if (StringUtils.isBlank(schoolId)) {
            //需要根据自定义的医院ID设值
        }
    }
    /**
     * 学校类别枚举
     */
    public enum Category {

        /**
         * 公立
         */
        PUBLIC("公立"),
        /**
         * 私立
         */
        PRIVATE("私立");

        private String value;

        Category(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    /**
     * 学校ID
     */
    @Id
    @Column(name = "id", length = 32, updatable = false, unique = true)
    private String schoolId;

    /**
     * 学校名称
     */
    @Column(name = "name", length = 100)
    private String name;

    /**
     * 二维码打印专用学校名称
     */
    @Column(name = "print_name", length = 100)
    private String printName;

    /**
     * 学校名称简写
     */
    @Column(name = "short_name", length = 100)
    private String shortName;

    /**
     * 学校Code
     */
    @Column(name = "code", length = 100)
    private String code;

    /**
     * 学校类别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50)
    private Category category;

    /**
     * 所在省份
     */
    @Column(name = "province", length = 50)
    private String province;

    /**
     * 地址
     */
    @Column(name = "address", length = 100)
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "tel", length = 32)
    private String tel;

    /**
     * 联系人
     */
    @Column(name = "contact", length = 32)
    private String contact;

    /**
     * 联系人电话
     */
    @Column(name = "contact_tel", length = 32)
    private String contactTel;

    /**
     * 联系人邮箱
     */
    @Column(name = "contact_mail", length = 64)
    private String contactMail;

    /**
     * 是否排查完成
     */
    @Column(name = "is_check_finish")
    private Boolean isCheckFinish;

    /**
     * 排查开始时间
     */
    @Column(name = "check_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkStartTime;

    /**
     * 预计排查结束时间
     */
    @Column(name = "check_end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkEndTime;

    /**
     * 实际排查结束时间
     */
    @Column(name = "finish_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;

    /**
     * 是否导入台账
     */
    @Column(name = "is_ledger_import")
    private Boolean isLedgerImport;

    /**
     * 是否结转
     */
    @Column(name = "is_data_flow")
    private Integer isDataFlow;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

    /**
     * 地域
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", foreignKey = @ForeignKey(name = "fk1_school"))
    private Area area;

}
