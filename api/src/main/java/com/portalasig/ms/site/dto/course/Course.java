package com.portalasig.ms.site.dto.course;

import com.portalasig.ms.site.constant.CourseType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "A college course")
public class Course {

    @ApiModelProperty(value = "Course id")
    private Integer courseId;

    @ApiModelProperty(value = "Semester id")
    private Integer semesterId;

    @ApiModelProperty(value = "Course identifier")
    private String code;

    @ApiModelProperty(value = "Course name")
    private String name;

    @ApiModelProperty(value = "Course amount of credit units")
    private Integer creditUnits;

    @ApiModelProperty(value = "Type of courses")
    private CourseType type;

    @ApiModelProperty(value = "Course requirements")
    private String requirements;

}
