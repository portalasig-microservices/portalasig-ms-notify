package com.portalasig.ms.site.repository;

import com.portalasig.ms.site.domain.entity.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassificationRepository extends JpaRepository<ClassificationEntity, Integer> {
    List<ClassificationEntity> findAllByClassificationIdIn(List<Integer> classificationIds);
}
