package ru.clevertec.util;

public class EnumUtil{
    public static <T extends Enum> T getEnumItemByName(T[] items, String name, T defaultItem){
        for (T value : items) {
            if(value == defaultItem) continue;
            if(value.name().equals(name)) return value;
        }
        return defaultItem;
    }
}
