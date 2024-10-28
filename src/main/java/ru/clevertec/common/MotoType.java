package ru.clevertec.common;

import static ru.clevertec.util.EnumUtil.getEnumItemByName;

public enum MotoType {
    ENDURO,
    MOTOCROS,
    CLASSIC,
    SPORT,
    NAKED,
    CHOPPER,
    CRUISER,
    TOURING,
    SCOOTER,
    OTHER;

    public static MotoType build(String name) {
        return getEnumItemByName(MotoType.values(), name, OTHER);
    }
}
