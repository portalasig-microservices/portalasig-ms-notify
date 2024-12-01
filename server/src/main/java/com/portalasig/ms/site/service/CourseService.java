package com.portalasig.ms.site.service;

import com.portalasig.ms.commons.rest.dto.Paginated;
import com.portalasig.ms.commons.rest.exception.ConflictException;
import com.portalasig.ms.commons.rest.exception.ResourceNotFoundException;
import com.portalasig.ms.site.domain.entity.CareerEntity;
import com.portalasig.ms.site.domain.entity.ClassificationEntity;
import com.portalasig.ms.site.domain.entity.CourseEntity;
import com.portalasig.ms.site.dto.course.Course;
import com.portalasig.ms.site.dto.course.CourseRequest;
import com.portalasig.ms.site.mapper.CourseMapper;
import com.portalasig.ms.site.repository.CareerRepository;
import com.portalasig.ms.site.repository.ClassificationRepository;
import com.portalasig.ms.site.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    private final CareerRepository careerRepository;

    private final ClassificationRepository classificationRepository;

    private final CourseMapper courseMapper;

    public Paginated<Course> findAll(Pageable pageable) {
        Page<CourseEntity> courses = courseRepository.findAll(pageable);
        if (courses.isEmpty()) {
            throw new ResourceNotFoundException("No courses found");
        }
        return Paginated.wrap(courses.map(courseMapper::toDto));
    }

    @Transactional
    public Course upsert(CourseRequest request) {
        CourseEntity course;
        List<CareerEntity> careers = careerRepository.findAllById(request.getCareers());
        List<ClassificationEntity> classifications = classificationRepository.findAllById(request.getClassifications());
        if (careers.isEmpty() || classifications.isEmpty()) {
            throw new ResourceNotFoundException("No careers or classifications found. Skipping Course upsert");
        }
        if (request.getCourseId() == null) {
            if (courseRepository.findByCode(request.getCode()).isPresent()) {
                throw new ConflictException(
                        String.format("Course with code=%s already exists. Skipping Course creation", request.getCode())
                );
            }
            course = courseMapper.toEntity(request);
            course.setCareers(new HashSet<>(careers));
            course.setClassifications(new HashSet<>(classifications));
        } else {
            course = courseRepository.findById(request.getCourseId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("Course with course_id=%s not found. Skipping update", request.getCourseId())
                    )
            );
            course.getCareers().clear();
            course.getClassifications().clear();
            course.setCareers(new HashSet<>(careers));
            course.setClassifications(new HashSet<>(classifications));
            courseMapper.toEntityFromExisting(course, request);
        }

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
