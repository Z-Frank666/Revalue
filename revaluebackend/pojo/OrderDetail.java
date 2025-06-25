package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private Integer id;
    private String name;
    private String description;
    private Integer itemId;
    private Integer buyerId;
    private String buyerName;
    private Integer sellerId;
    private String sellerName;
    private Double price;
    private LocalDateTime createTime;
    private String image;
}
