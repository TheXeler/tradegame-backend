package com.thexeler.whin.dto.combat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CombatOperate {
    private OperateType operateType;
    private Long squadId;
    private Map<String, String> parameters;


    public CombatOperate(CombatOperateRequest request) {
        operateType = OperateType.valueOf(request.getOperation().toUpperCase());

        Gson gson = new Gson();
        parameters = gson.fromJson(request.getData(), new TypeToken<Map<String, String>>() {}.getType());

        squadId = Long.parseLong(parameters.get("squadId"));
    }

    public int getPosX() {
        return Integer.parseInt(parameters.get("x"));
    }

    public int getPosY() {
        return Integer.parseInt(parameters.get("y"));
    }

}
