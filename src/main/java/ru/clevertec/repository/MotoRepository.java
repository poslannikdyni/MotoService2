package ru.clevertec.repository;

import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;
import ru.clevertec.entity.MotoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MotoRepository {

    // Create + Update
    MotoEntity save(MotoEntity motoEntity);

    void delete(UUID id);

    Optional<MotoEntity> get(UUID id);

    List<MotoEntity> getByBrand(MotoBrand brand);

    List<MotoEntity> getByType(MotoType type);

    List<MotoEntity> getBetweenCapacity(int fromCapacity, int toCapacity);

    List<MotoEntity> getAll();
}
