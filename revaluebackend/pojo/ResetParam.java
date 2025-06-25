package com.frank.revaluebackend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetParam {
    private String email;
    private String newPassword;
    private String confirmPassword;
    private String captcha;
}
