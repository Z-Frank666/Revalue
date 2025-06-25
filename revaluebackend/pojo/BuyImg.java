package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyImg {
    private Integer id;
    private String image;
    private Integer itemId;
}
