package com.portalasig.ms.site.domain.entity;

import com.portalasig.ms.commons.persistence.AbstractAuditEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "semester")
@Builder
public class SemesterEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semester_id")
    private Integer semesterId;

    // TODO: Add relationship with SiteEntity

    @NotNull
    @Column(name = "academic_period", length = 32)
    private String academicPeriod;

    @Column(name = "description", length = 128)
    private String description;

    @NotNull
    @Column(name = "name", length = 128)
    private String name;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "course_semester",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SemesterEntity that = (SemesterEntity) o;
        return semesterId != null && semesterId.equals(that.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterId);
    }
}
