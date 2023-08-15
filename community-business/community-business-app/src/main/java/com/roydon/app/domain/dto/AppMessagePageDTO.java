package com.roydon.app.domain.dto;

import com.roydon.app.domain.entity.AppMessage;
import lombok.Data;

@Data
public class AppMessagePageDTO extends AppMessage {

    private Integer pageNum;

    private Integer pageSize;

}
