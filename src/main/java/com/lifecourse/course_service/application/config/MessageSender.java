package com.lifecourse.course_service.application.config;

public interface MessageSender<T> {
    void send( T eventMessage);
}
