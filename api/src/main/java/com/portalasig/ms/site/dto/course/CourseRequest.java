package com.portalasig.ms.site.dto.course;

import com.portalasig.ms.site.constant.CourseType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "A college course request")
public class CourseRequest {

    @ApiModelProperty(value = "Course id")
    private Integer courseId;

    @ApiModelProperty(value = "Semester id")
    @NotNull
    private Integer semesterId;

    @ApiModelProperty(value = "Course code")
    @NotNull
    private String code;

    @ApiModelProperty(value = "Course name")
    @NotNull
    private String name;

    @ApiModelProperty(value = "Course amount of credit units")
    @NotNull
    private Integer creditUnits;

    @ApiModelProperty(value = "Type of courses")
    @NotNull
    private CourseType type;

    @ApiModelProperty(value = "Course requirements")
    @NotNull
    private String requirements;

    @ApiModelProperty(value = "Course section")
    @NotNull
    private String section;

    @ApiModelProperty(value = "Course career")
    @NotNull
    private String career;

    @ApiModelProperty(value = "Course classification")
    @NotNull
    private String classification;

}
