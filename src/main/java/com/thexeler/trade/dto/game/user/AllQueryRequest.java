package com.thexeler.trade.dto.game.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllQueryRequest {
    private String token;
    private String username = "";
}
