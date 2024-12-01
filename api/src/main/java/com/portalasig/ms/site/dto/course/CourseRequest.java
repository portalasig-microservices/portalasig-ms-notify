package com.portalasig.ms.site.dto.course;

import com.portalasig.ms.site.constant.CourseType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a request to create or update a college course.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "A college course request")
public class CourseRequest {

    /**
     * The unique identifier of the course.
     */
    @ApiModelProperty(value = "Course id")
    private Integer courseId;

    /**
     * The unique code for the course.
     */
    @ApiModelProperty(value = "Course code")
    @NotNull
    private String code;

    /**
     * The name of the course.
     */
    @ApiModelProperty(value = "Course name")
    @NotNull
    private String name;

    /**
     * The number of credit units assigned to the course.
     */
    @ApiModelProperty(value = "Course amount of credit units")
    @NotNull
    private Integer creditUnits;

    /**
     * The type of course.
     */
    @ApiModelProperty(value = "Type of courses")
    @NotNull
    private CourseType type;

    /**
     * The prerequisites for the course.
     */
    @ApiModelProperty(value = "Course requirements")
    @NotNull
    private String requirements;

    /**
     * The list of career IDs associated with the course.
     */
    @ApiModelProperty(value = "List of careers ids associated with the course")
    @NotNull
    private List<Integer> careers;

    /**
     * The list of classification IDs associated with the course.
     */
    @ApiModelProperty(value = "List of classifications ids associated with the course")
    @NotNull
    private List<Integer> classifications;
}
