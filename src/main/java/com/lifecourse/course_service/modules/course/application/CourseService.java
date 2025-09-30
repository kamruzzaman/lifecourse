package com.lifecourse.course_service.modules.course.application;

import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.domain.ChapterBR;
import com.lifecourse.course_service.modules.course.domain.CourseBR;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseEntity;
import com.lifecourse.course_service.modules.course.infrastructure.persistence.CourseRepositoryAdapter;
import com.lifecourse.course_service.modules.course.web.dto.CourseResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;

@Service
public class CourseService  {

    private final CourseRepositoryAdapter course;

    public CourseService(CourseRepositoryAdapter course) {
        this.course = course;
    }

    public ApiResponse getAllCourses(Pageable pageable) {
         CourseBR courseBR =new CourseBR();
         Page<CourseEntity> page= course.findAll(pageable);
         return courseBR.getAllCourses(page);
    }

    public ApiResponse getAllCoursesByUser(String username,Pageable pageable) {
        CourseBR courseBR =new CourseBR();
        Page<CourseEntity> page= course.findAllByUserName(username,pageable);
        return courseBR.getAllCourses(page);
    }


    public ApiResponse getCourseById(Long id) {
        return  new ApiResponse(200,"Course Found",course.findById(id));
    }

    public ApiResponse createCourse(CreateCourseRequest createCourseRequest) {
        CourseResponse courseResponse  = course.createCourse(createCourseRequest);
        return new ApiResponse(200,"created ",courseResponse);
    }

    public ApiResponse updateCourse(Long id, CreateCourseRequest createCourseRequest) {
        CourseResponse courseResponse  = course.editCourse(id,createCourseRequest);
        if(!ObjectUtils.isEmpty(courseResponse)) {
            return new ApiResponse(200, "Course updated ", courseResponse);
        }
        return new ApiResponse(200, "Failed to Course updated ", null);
    }

    public ApiResponse deleteCourse(Long id) {
        course.delete(id);
        return new ApiResponse(200,"course deleted successfully ", Collections.emptyList());
    }



    public ApiResponse getCourseStats(Long id) {
            return null;
    }
}
