package com.ford.asukathunder;

import com.ford.asukathunder.common.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: CommonConfig
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/13 上午 10:44
 **/
@Configuration
public class CommonConfig {
    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(0, 0);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
