package com.portalasig.ms.site.mapper;


import com.portalasig.ms.commons.mapper.EnumStringMapper;
import com.portalasig.ms.site.constant.CourseType;
import com.portalasig.ms.site.domain.entity.CourseEntity;
import com.portalasig.ms.site.domain.entity.SemesterEntity;
import com.portalasig.ms.site.dto.course.Course;
import com.portalasig.ms.site.dto.course.CourseRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.Set;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {EnumStringMapper.class, CourseType.class, HashSet.class, Set.class}
)
public interface CourseMapper {

    @Mapping(
            target = "type",
            expression = "java(EnumStringMapper.fromStringToEnum(courseEntity.getType(), CourseType.class))"
    )
    Course toDto(CourseEntity courseEntity);

    @Mapping(
            target = "type",
            expression = "java(EnumStringMapper.fromEnumToString(request.getType()))"
    )
    @Mapping(target = "semesters", expression = "java(new HashSet<>(Set.of(semester)))")
    @Mapping(target = "name", source = "request.name")
    CourseEntity toEntity(CourseRequest request, @Context SemesterEntity semester);

    @Mapping(
            target = "type",
            expression = "java(EnumStringMapper.fromEnumToString(request.getType()))"
    )
    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "semesters", ignore = true)
    void toEntityFromExisting(CourseRequest request, @Context SemesterEntity semester, @MappingTarget CourseEntity course);

    @AfterMapping
    default void updateSemestersFromCourse(
            CourseRequest request,
            @Context SemesterEntity semester,
            @MappingTarget CourseEntity course
    ) {
        Set<SemesterEntity> semesters = course.getSemesters();
        semesters.clear();
        if (semester != null) {
            semesters.add(semester);
        }
    }
}
