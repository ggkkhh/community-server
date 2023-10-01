package com.roydon.business.epidemic.domain.dto;

import com.roydon.business.epidemic.domain.entity.EpidemicNatOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EpidemicNatOrderPageDTO extends EpidemicNatOrder {

    private Integer pageNum;

    private Integer pageSize;

}
