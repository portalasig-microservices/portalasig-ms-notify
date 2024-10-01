package com.portalasig.ms.site.domain.entity;

import com.portalasig.ms.commons.persistence.AbstractAuditEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Builder
public class CourseEntity extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Integer creditUnits;

    @NotNull
    private String type;

    @NotNull
    private String requirements;

    @NotNull
    private String section;

    @NotNull
    private String career;

    @NotNull
    private String classification;

    @ManyToMany(
            mappedBy = "courses",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Set<SemesterEntity> semesters;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return courseId != null && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}
