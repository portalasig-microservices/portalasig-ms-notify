package com.portalasig.ms.site.dto.semester;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "A college semester")
public class Semester {

    @ApiModelProperty(value = "Semester identifier")
    private Integer semesterId;

    @ApiModelProperty(value = "Semester academic period")
    private String academicPeriod;

    @ApiModelProperty(value = "Semester description")
    private String description;

    @ApiModelProperty(value = "Semester name")
    private String name;

    @ApiModelProperty(value = "Semester start date")
    private LocalDate startDate;

    @ApiModelProperty(value = "Semester end date")
    private LocalDate endDate;

    @ApiModelProperty(value = "Semester is active")
    private Boolean isActive;

}
