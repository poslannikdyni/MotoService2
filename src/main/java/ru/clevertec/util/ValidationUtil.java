package ru.clevertec.util;

import ru.clevertec.entity.AbstractEntity;
import ru.clevertec.exception.NotFoundException;

import java.util.UUID;

public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, UUID id) {
        if (object == null) {
            throw new NotFoundException("Not found entity with id = " + id);
        }
        return object;
    }

    public static void checkNew(AbstractEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }
}
