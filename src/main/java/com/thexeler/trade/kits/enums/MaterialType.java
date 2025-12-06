package com.thexeler.trade.kits.enums;

public enum MaterialType {
    HARDWARE,
    BUILDING,
    ELECTRICAL,
    CHEMICAL;

    public static MaterialType getMaterialType(String name) {
        for (MaterialType type : MaterialType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static String getMaterialTypeName(MaterialType type) {
        return type.name();
    }
}
