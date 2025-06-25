package com.frank.revaluebackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mybuy {
    private Integer id;
    private String title;
    private String description;
    private Integer sortId;
    private String tag;
    private String time;
    private Integer imgCnt;
    private Boolean isactive;
}
