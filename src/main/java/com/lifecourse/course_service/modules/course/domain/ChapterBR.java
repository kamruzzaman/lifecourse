package com.lifecourse.course_service.modules.course.domain;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.ChapterEntity;
import org.springframework.data.domain.Page;

import static com.lifecourse.course_service.modules.course.utils.DataPageUtil.convertToDatapage;

public class ChapterBR {


    public ApiResponse getAllChapters(Page<ChapterEntity> page) {
        return null;
    }


    public ApiResponse getAllCourseId(Page<ChapterEntity> byCourseId) {
        return  null;
    }
}
