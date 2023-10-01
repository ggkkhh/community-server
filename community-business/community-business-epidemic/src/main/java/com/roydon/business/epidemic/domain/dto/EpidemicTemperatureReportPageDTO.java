package com.roydon.business.epidemic.domain.dto;

import com.roydon.business.epidemic.domain.entity.EpidemicTemperatureReport;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * EpidemicTemperatureReportPageDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/9/25
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EpidemicTemperatureReportPageDTO extends EpidemicTemperatureReport {

    private Integer pageNum;

    private Integer pageSize;

}
