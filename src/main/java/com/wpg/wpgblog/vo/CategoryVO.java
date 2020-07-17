package com.wpg.wpgblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryVO {

    private Integer id;

    private String name;

    private String uniqueString;
}
