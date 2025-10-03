package com.lifecourse.course_service.application.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    private final SnowflakeProperties properties;

    public SnowflakeConfig(SnowflakeProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(
                properties.getWorkerId(),
                properties.getDatacenterId()
        );
    }
}
