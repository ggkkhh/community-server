package com.roydon.sms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AliSmsResponce
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliSmsResponse {
    private String Message ;
    private String RequestId;
    private String Code;
    private String BizId;

}
