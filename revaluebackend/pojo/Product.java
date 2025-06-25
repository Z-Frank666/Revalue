package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    private Integer id;
    private String title;
    private Integer price;
    private Integer originalPrice;
    private String image;
    private String shop;
    private Integer sortId;
}
