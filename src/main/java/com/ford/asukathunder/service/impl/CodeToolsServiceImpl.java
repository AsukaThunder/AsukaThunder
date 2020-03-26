package com.ford.asukathunder.service.impl;

import com.ford.asukathunder.common.entity.base.CodeTools;
import com.ford.asukathunder.common.exception.ResourceNotFoundException;
import com.ford.asukathunder.repository.CodeToolsRepository;
import com.ford.asukathunder.service.CodeToolsService;
import com.ford.asukathunder.util.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: CodeToolsServiceImpl
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 5:31
 **/
@Service
public class CodeToolsServiceImpl implements CodeToolsService {

    @Resource
    private CodeToolsRepository codeToolsRepo;

    @Override
    public synchronized int getOrder(String today, String codeType) {
        int order = 1;
        if (StringUtils.isEmpty(today)) {
            throw new IllegalArgumentException(ErrorCode.CodeTodayEmpty.name());
        }
        CodeTools codeTools = codeToolsRepo.findByCode(codeType);
        if (codeTools == null) {
            throw new ResourceNotFoundException(ErrorCode.CodeNotFound);
        }
        String dbDate = codeTools.getDate();
        if (today.equals(dbDate)) {
            //order+1
            int dbOrder = codeTools.getOrderNum();
            order = ++dbOrder;
            codeTools.setOrderNum(order);
        } else {
            codeTools.setOrderNum(order);
            codeTools.setDate(today);
        }
        //插入数据库
        codeTools = codeToolsRepo.save(codeTools);
        return codeTools.getOrderNum();
    }

    @Override
    public int getOrder(String codeType) {
        CodeTools codeTools = codeToolsRepo.findByCode(codeType);
        if (codeTools == null) {
            throw new ResourceNotFoundException(ErrorCode.CodeNotFound);
        }
        int dbOrder = codeTools.getOrderNum();
        int order = ++dbOrder;
        codeTools.setOrderNum(order);
        codeTools = codeToolsRepo.save(codeTools);
        return codeTools.getOrderNum();
    }
}
