package com.lifecourse.course_service.application.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private final Snowflake snowflake;

    public SnowflakeIdGenerator(Snowflake snowflake) {
        this.snowflake = snowflake;
    }

    public long nextId() {
        return snowflake.nextId();
    }
}
