package com.frank.revaluebackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private String content;
    private String time;
    private LocalDateTime createTime;
    private Integer likes;
    private String avatar;
    private String nickname;
}
