package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.jpa.other.Article;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @ClassName: ArticleRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/20 下午 6:04
 **/
public interface ArticleRepository extends JpaRepositoryImplementation<Article,Long> {
}
