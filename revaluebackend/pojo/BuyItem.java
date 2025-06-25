package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyItem {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private Integer sortId;
    private Integer imgCnt;
    private LocalDateTime createTime;
    private String QQ;
    private String weixin;
    private String phone;
    private String name;
    private Boolean isactive = true;
}
