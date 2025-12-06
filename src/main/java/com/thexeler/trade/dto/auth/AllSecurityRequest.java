package com.thexeler.trade.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllSecurityRequest {
    private String username;
    private String password;
    private String token;
}
