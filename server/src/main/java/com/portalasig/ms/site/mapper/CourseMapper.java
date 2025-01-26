package com.portalasig.ms.site.mapper;


import com.portalasig.ms.commons.mapper.EnumStringMapper;
import com.portalasig.ms.site.constant.CourseType;
import com.portalasig.ms.site.domain.entity.CareerEntity;
import com.portalasig.ms.site.domain.entity.ClassificationEntity;
import com.portalasig.ms.site.domain.entity.CourseEntity;
import com.portalasig.ms.site.dto.course.Course;
import com.portalasig.ms.site.dto.course.CourseRequest;
import com.portalasig.ms.site.dto.course.CsvCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.List;
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
    @Mapping(target = "semesters", ignore = true)
    @Mapping(target = "careers", ignore = true)
    @Mapping(target = "classifications", ignore = true)
    CourseEntity toEntity(CourseRequest request);

    @Mapping(
            target = "type",
            expression = "java(EnumStringMapper.fromEnumToString(request.getType()))"
    )
    @Mapping(target = "semesters", ignore = true)
    @Mapping(target = "careers", ignore = true)
    @Mapping(target = "classifications", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "courseId", ignore = true)
    void toEntityFromExisting(@MappingTarget CourseEntity course, CourseRequest request);

    @Mapping(target = "semesters", ignore = true)
    @Mapping(target = "careers", ignore = true)
    @Mapping(target = "classifications", ignore = true)
    CourseEntity toEntityFromCsv(CsvCourse courseCsv);

    default List<Integer> flatCareers(Set<CareerEntity> careers) {
        return careers.stream().map(CareerEntity::getCareerId).toList();
    }

    default List<Integer> flatClassifications(Set<ClassificationEntity> classifications) {
        return classifications.stream().map(ClassificationEntity::getClassificationId).toList();
    }
}
