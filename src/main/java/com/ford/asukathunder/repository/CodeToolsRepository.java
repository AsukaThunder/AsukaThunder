package com.ford.asukathunder.repository;

import com.ford.asukathunder.common.entity.base.CodeTools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName: CodeToolsRepository
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 5:32
 **/
public interface CodeToolsRepository extends JpaRepository<CodeTools, Integer>, JpaSpecificationExecutor<CodeTools> {
    CodeTools findByCode(String code);
}
