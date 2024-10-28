package ru.clevertec.util;

import ru.clevertec.entity.MotoEntity;

import java.io.IOException;
import java.util.List;

public class MotoUtils {
    public static final List<MotoEntity> MOTO_ENTITY_LIST;

    static {
        try {
            MOTO_ENTITY_LIST = CsvHelper.readMoto(MotoUtils.class.getClassLoader().getResource("moto.csv").getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
