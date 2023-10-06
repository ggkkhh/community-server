package com.roydon.business.epidemic.domain.dto;

import com.roydon.business.epidemic.domain.entity.EpidemicAccessRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * EpidemicAccessRecordPageDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/10/3
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class EpidemicAccessRecordPageDTO extends EpidemicAccessRecord {

    private Integer pageNum;

    private Integer pageSize;

}
