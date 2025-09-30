package com.lifecourse.course_service.modules.course.web;

import com.lifecourse.course_service.modules.course.application.CourseService;
import com.lifecourse.course_service.modules.course.config.ApiResponse;
import com.lifecourse.course_service.modules.course.web.dto.CreateCourseRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // GET /courses - List all courses with pagination + optional filters
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @GetMapping("by/{username}")
    public ResponseEntity<ApiResponse> getAllCoursesByUser(@PathVariable String username, Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCoursesByUser(username,pageable));
    }

    // GET /courses/{id} - Get course details
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse>  getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // POST /courses - Create new course
    @PostMapping
    public ResponseEntity<ApiResponse>  createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest ) {
        return ResponseEntity.ok(courseService.createCourse(createCourseRequest));
    }

    // PUT /courses/{id} - Update existing course
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Long id,
                                                  @Valid @RequestBody CreateCourseRequest courseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }

    // DELETE /courses/{id} - Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok( courseService.deleteCourse(id));
    }



    // POST /courses/{id}/thumbnail - Upload course thumbnail
//    @PostMapping("/{id}/thumbnail")
//    public ResponseEntity<ApiResponse>  uploadThumbnail(@PathVariable Long id,
//                                                  @RequestParam("file") MultipartFile file) {
//        String imageUrl = courseService.uploadThumbnail(id, file);
//        return ResponseEntity.ok(imageUrl);
//    }

    // GET /courses/{id}/stats - Course-level analytics (enrollments, views)
    @GetMapping("/{id}/stats")
    public ResponseEntity<ApiResponse>  getCourseStats(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseStats(id));
    }
}
