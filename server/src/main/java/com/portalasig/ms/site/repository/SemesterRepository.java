package com.portalasig.ms.site.repository;

import com.portalasig.ms.site.domain.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface
SemesterRepository extends JpaRepository<SemesterEntity, Integer> {

    @Query("""
            SELECT semester
            FROM SemesterEntity semester
            WHERE semester.isActive = true
            """)
    Optional<SemesterEntity> getActiveSemester();

    Optional<SemesterEntity> findByAcademicPeriod(String academicPeriod);
}
