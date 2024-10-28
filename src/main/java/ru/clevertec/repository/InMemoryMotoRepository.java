package ru.clevertec.repository;

import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;
import ru.clevertec.entity.MotoEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static ru.clevertec.util.MotoUtils.MOTO_ENTITY_LIST;

public class InMemoryMotoRepository implements MotoRepository {

    private static final MotoRepository INSTANCE = new InMemoryMotoRepository();
    public static MotoRepository getInstance() {
        return INSTANCE;
    }


    private final Map<UUID, MotoEntity> container = new ConcurrentHashMap<>();

    public InMemoryMotoRepository() {
        MOTO_ENTITY_LIST.forEach(this::save);
    }

    @Override
    public MotoEntity save(MotoEntity motoEntity) {
        if (motoEntity.isNew()) {
            motoEntity.setId(UUID.randomUUID());
            container.put(motoEntity.getId(), motoEntity);
            return motoEntity;
        }
        return container.computeIfPresent(motoEntity.getId(), (id, entity) -> motoEntity);
    }

    @Override
    public void delete(UUID id) {
        MotoEntity moto = container.get(id);
        if (moto != null) container.remove(id);
    }

    @Override
    public Optional<MotoEntity> get(UUID id) {
        return Optional.ofNullable(container.get(id));
    }

    @Override
    public List<MotoEntity> getByBrand(MotoBrand brand) {
        return getData(moto -> moto.getBrand() == brand,
                Comparator.comparing(MotoEntity::getBrand));
    }

    @Override
    public List<MotoEntity> getByType(MotoType type) {
        return getData(moto -> moto.getMotoType() == type,
                Comparator.comparing(MotoEntity::getBrand));
    }

    @Override
    public List<MotoEntity> getBetweenCapacity(int fromCapacity, int toCapacity) {
        return getData(moto -> fromCapacity <= moto.getCapacity() && moto.getCapacity() <= toCapacity,
                Comparator.comparing(MotoEntity::getCapacity));
    }

    @Override
    public List<MotoEntity> getAll() {
        return new ArrayList<>(container.values());
    }

    private List<MotoEntity> getData(Predicate<MotoEntity> filterBy, Comparator<MotoEntity> sortBy) {
        return getData(true, filterBy, sortBy);
    }

    private List<MotoEntity> getData(boolean excludeNull, Predicate<MotoEntity> filterBy, Comparator<MotoEntity> sortBy) {
        var stream = container.values().stream();

        if (excludeNull)
            stream = stream.filter(Objects::nonNull);

        if (filterBy != null)
            stream = stream.filter(filterBy);

        if (sortBy != null)
            stream = stream.sorted(sortBy);

        return stream.toList();
    }
}
