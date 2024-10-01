package com.portalasig.ms.site.service;

import com.portalasig.ms.commons.rest.dto.Paginated;
import com.portalasig.ms.commons.rest.exception.ResourceNotFoundException;
import com.portalasig.ms.site.domain.entity.CourseEntity;
import com.portalasig.ms.site.domain.entity.SemesterEntity;
import com.portalasig.ms.site.dto.course.Course;
import com.portalasig.ms.site.dto.course.CourseRequest;
import com.portalasig.ms.site.mapper.CourseMapper;
import com.portalasig.ms.site.repository.CourseRepository;
import com.portalasig.ms.site.repository.SemesterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    private final SemesterRepository semesterRepository;

    private final CourseMapper courseMapper;

    public Paginated<Course> findAll(Pageable pageable) {
        Page<CourseEntity> courses = courseRepository.findAll(pageable);
        if (courses.isEmpty()) {
            throw new ResourceNotFoundException("No courses found");
        }
        return Paginated.wrap(courses.map(courseMapper::toDto));
    }

    public Course upsert(CourseRequest request) {
        CourseEntity course;
        SemesterEntity semester = semesterRepository.findById(request.getSemesterId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Semester with semester_id=%s not found", request.getSemesterId())
                )
        );
        if (request.getCourseId() == null) {
            course = courseMapper.toEntity(request, semester);
        } else {
            course = courseRepository.findById(request.getCourseId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("Course with course_id=%s not found", request.getCourseId())
                    )
            );
            courseMapper.toEntityFromExisting(request, semester, course);
        }
        course = courseRepository.save(course);
        semester.getCourses().add(course);
        course.getSemesters().add(semester);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Transactional
    public void delete(Integer courseId) {
        courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Course with course_id=%s not found", courseId)
                )
        );
        try {
            courseRepository.deleteById(courseId);
        } catch (Exception e) {
            log.error("Error deleting course with course_id={}", courseId, e);
            throw new ResourceNotFoundException(
                    String.format("Error deleting course with course_id=%s", courseId)
            );
        }
    }
}
