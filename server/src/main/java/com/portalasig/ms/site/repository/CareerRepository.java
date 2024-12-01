package com.portalasig.ms.site.repository;

import com.portalasig.ms.site.domain.entity.CareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<CareerEntity, Integer> {
}
