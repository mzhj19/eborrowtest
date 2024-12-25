package com.mzhj19.eborrow.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginDto {
    private String email;
    private String password;
}
