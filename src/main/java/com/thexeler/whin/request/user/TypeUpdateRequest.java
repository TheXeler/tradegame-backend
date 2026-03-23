package com.thexeler.whin.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeUpdateRequest {
    private String token;
    private String username = "";
    private String type = "";
    private String value = "";
    private String description = "";
}
