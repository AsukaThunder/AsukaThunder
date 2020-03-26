package com.ford.asukathunder.common.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CODE生成工具表
 * @ClassName: CodeTools
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 5:33
 **/
@Entity
@Table(name = "code_tools")
@DynamicUpdate
@Getter
@Setter
public class CodeTools {

    @Id
    @Column(name = "code", length = 32, updatable = false, unique = true)
    private String code;

    /**
     * code名
     */
    @Column(name = "code_name")
    private String codeName;

    /**
     * 末次生成日期
     */
    @Column(name = "date")
    private String date;

    /**
     * 序号
     */
    @Column(name = "order_num")
    private Integer orderNum;
}

