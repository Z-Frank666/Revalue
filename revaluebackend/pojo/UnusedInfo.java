package com.frank.revaluebackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnusedInfo {
    private Integer id;
    private Integer userId;
    private String name;
    private String description;
    private Double price;
    private String linkman;
    private String phone;
    private Integer sortId;
    private String tag;
    private String image;
    private LocalDateTime createTime;
    private Boolean isactive;
}
