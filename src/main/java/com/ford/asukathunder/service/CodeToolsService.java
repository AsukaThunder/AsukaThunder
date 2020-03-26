package com.ford.asukathunder.service;

/**
 * @ClassName: CodeToolsService
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/3/12 下午 5:30
 **/
public interface CodeToolsService {

    int getOrder(String today, String codeType);

    int getOrder(String codeType);
}
