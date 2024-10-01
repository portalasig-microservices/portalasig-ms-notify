package com.portalasig.ms.site.dto.semester;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "A college semester request")
public class SemesterRequest {

    @ApiModelProperty(value = "Semester id")
    private Integer semesterId;

    @ApiModelProperty(value = "Academic period")
    @NotNull
    private String academicPeriod;

    @ApiModelProperty(value = "Name")
    @NotNull
    private String name;

    @ApiModelProperty(value = "Start date")
    @NotNull
    private LocalDate startDate;

    @ApiModelProperty(value = "End date")
    @NotNull
    private LocalDate endDate;

    @ApiModelProperty(value = "Description")
    private String description;

}
