package com.ford.asukathunder.common.encrypt.auto;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName: EncryptAutoConfiguration
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
@Configuration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptAutoConfiguration {

}
