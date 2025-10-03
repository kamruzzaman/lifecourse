package com.lifecourse.course_service.application.config;

import cn.hutool.core.codec.Base62;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;

public class SnowflakeEntityListener {
    private static SnowflakeIdGenerator idGenerator;

    @Autowired
    public void init(SnowflakeIdGenerator generator) {
        SnowflakeEntityListener.idGenerator = generator;
    }
    private byte[] longToByteArray(Long id) {
        if (id == null)  throw new IllegalArgumentException(); // or throw IllegalArgumentException
        byte[] bytes = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            bytes[Long.BYTES - 1 - i] = (byte) (id >>> (i * 8));
        }
        return bytes;
    }
    @PrePersist
    public void prePersist(BaseEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.nextId());
        }
        if (entity.getPublicId() == null) {
            entity.setPublicId(Base62.encode(longToByteArray(entity.getId()))); // or Hashids
        }
    }
}
