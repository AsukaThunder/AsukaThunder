package com.ford.asukathunder.common.entity.jpa.other;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @ClassName: Article
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/18 下午 2:24
 **/
@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;

    @Lob  // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)// 映射为字段，值不能为空
    private String content; //文章全文内容

    /**
     * 可选属性optional=false,表示author不能为空。删除文章，不影响用户
     */
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="author_id")
    private Author author;
}
