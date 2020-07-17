package com.wpg.wpgblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenVO {

    private String token;

    private String email;

    private String avatar;

    private String username;
}
