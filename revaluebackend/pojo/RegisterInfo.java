package com.frank.revaluebackend.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInfo {
    private Integer id; //用户ID
    private String name; //姓名
    private String password; //密码
    private String email; //邮箱
}
