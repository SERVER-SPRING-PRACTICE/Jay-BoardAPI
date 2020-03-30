package com.cadi.boardapi.model;

import lombok.Data;

@Data
public class SignUpReq {

    private String id;
    private String password;
    private String confirmPw;
    private String name;
    private String phone;
    private String email;

}
