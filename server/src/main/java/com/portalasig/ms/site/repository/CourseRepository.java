package com.portalasig.ms.site.repository;

import com.portalasig.ms.site.domain.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
}
