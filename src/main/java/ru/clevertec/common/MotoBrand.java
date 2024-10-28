package ru.clevertec.common;

import static ru.clevertec.util.EnumUtil.getEnumItemByName;

public enum MotoBrand {
    KAWASAKI,
    HONDA,
    ROYAL_ENFIELD,
    SUZUKI,
    YAMAHA,
    TRIUMPH,
    BMW,
    APRILIA,
    DUCATI,
    HUSQVARNA,
    OTHER;

    public static MotoBrand build(String name) {
        return getEnumItemByName(MotoBrand.values(), name, OTHER);
    }
}
