package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnusedImg {
    private Integer id;
    private String image;
    private Integer itemId;
}
