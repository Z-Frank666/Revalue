package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnusedItem {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String linkman;
    private String phone;
    private Integer sortId;
    private String image;
    private Integer userId;
    private LocalDateTime createTime;
    private Boolean isactive = true;
}
