package com.thexeler.trade.dto.game.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeQueryRequest {
    private String token;
    private String username = "";
    private String type = "";
}
