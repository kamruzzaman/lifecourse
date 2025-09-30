package com.lifecourse.course_service.modules.course.domain;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.config.DataPage;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import org.springframework.data.domain.Page;

import static com.lifecourse.course_service.modules.course.domain.DataPageUtil.convertToDatapage;

public class CourseBR {


    public ApiResponse getAllCourses(Page<CourseEntity>  pageable) {
            DataPage dataPage=convertToDatapage(pageable);
            return new ApiResponse(200,"Course Details Founds",dataPage);
    }

}
