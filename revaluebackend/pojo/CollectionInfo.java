package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionInfo {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer sortId;
    private LocalDateTime createTime;
    private String tag;
    private Integer unusedId;
    private Integer usedId;
    private Double price;
}
