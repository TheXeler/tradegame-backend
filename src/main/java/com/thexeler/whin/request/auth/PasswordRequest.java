package com.thexeler.whin.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String username;
    private String password;
}