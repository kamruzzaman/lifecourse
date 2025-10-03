package com.lifecourse.course_service.modules.course.web;

import com.lifecourse.course_service.modules.course.application.CourseService;
import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.utils.ResponseUtil;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;
    private final MessageSource messageSource;

    @Autowired
    public CourseController(CourseService courseService, MessageSource messageSource) {
        this.courseService = courseService;
        this.messageSource = messageSource;
    }

    // GET /courses - List all courses with pagination + optional filters
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCourses(Pageable pageable, Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                courseService.getAllCourses(pageable),
                messageSource,
                "get.success",
                locale
        );
    }

    @GetMapping("by/{username}")
    public ResponseEntity<ApiResponse> getAllCoursesByUser(@PathVariable String username, Pageable pageable,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                courseService.getAllCoursesByUser(username,pageable),
                messageSource,
                "get.success",
                locale
        );
    }

    @GetMapping("enroll/by/{userId}")
    public ResponseEntity<ApiResponse> getAllCoursesByEnroll(@PathVariable String username, Pageable pageable,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                courseService.getAllCoursesByUser(username,pageable),
                messageSource,
                "get.success",
                locale
        );
    }

    // GET /courses/{id} - Get course details
    @GetMapping("/{publicId}")
    public ResponseEntity<ApiResponse>  getCourseById(@PathVariable String publicId,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.GET,
                courseService.getCourseById(publicId),
                messageSource,
                "get.success",
                locale
        );
    }

    // POST /courses - Create new course
    @PostMapping
    public ResponseEntity<ApiResponse>  createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest ,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.POST,
                courseService.createCourse(createCourseRequest),
                messageSource,
                "course.created",
                locale
        );
    }

    // PUT /courses/{id} - Update existing course
    @PutMapping("/{publicId}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable String publicId,
                                                  @Valid @RequestBody CreateCourseRequest courseDto,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.PUT,
                courseService.updateCourse(publicId, courseDto),
                messageSource,
                "patch.success",
                locale
        );
    }

    // DELETE /courses/{id} - Delete a course
    @DeleteMapping("/{publicId}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable String publicId,Locale locale) {
        return ResponseUtil.buildResponse(
                HttpMethod.DELETE,
                courseService.deleteCourse(publicId),
                messageSource,
                "delete.success",
                locale
        );
    }

}
