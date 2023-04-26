package com.roydon.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AppLoginBody
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppLoginBody {
    private String username;
    private String password;
}
