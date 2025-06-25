package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    private String email;
    private String address;
    private Double balance;
    private String avatar;
    private Integer gender;
    private Date birthday;
    private String description;
}
