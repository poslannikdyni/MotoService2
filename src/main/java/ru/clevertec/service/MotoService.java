package ru.clevertec.service;

import lombok.NoArgsConstructor;
import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;
import ru.clevertec.domain.Moto;
import ru.clevertec.entity.MotoEntity;
import ru.clevertec.mapper.MotoMapper;
import ru.clevertec.repository.InMemoryMotoRepository;
import ru.clevertec.repository.MotoRepository;

import java.util.List;
import java.util.UUID;

import static ru.clevertec.util.ValidationUtil.checkNew;
import static ru.clevertec.util.ValidationUtil.checkNotFoundWithId;

@NoArgsConstructor
public class MotoService {
    private static final MotoService INSTANCE = new MotoService();
    public static MotoService getInstance() {
        return INSTANCE;
    }

    private MotoRepository motoRepository = InMemoryMotoRepository.getInstance();
    private MotoMapper motoMapper = MotoMapper.INSTANCE;

    public Moto create(Moto moto) {
        MotoEntity motoEntity = motoMapper.toEntity(moto);
        checkNew(motoEntity);
        MotoEntity createdEntity = motoRepository.save(motoEntity);
        checkNotFoundWithId(createdEntity, createdEntity.getId());
        return motoMapper.toDomain(createdEntity);
    }

    public Moto update(UUID motoId, Moto newMoto) {
        MotoEntity motoEntity = motoMapper.toEntity(newMoto);
        motoEntity.setId(motoId);
        MotoEntity updatedEntity = motoRepository.save(motoEntity);
        checkNotFoundWithId(updatedEntity, updatedEntity.getId());
        return motoMapper.toDomain(updatedEntity);
    }

    public void delete(UUID motoId) {
        motoRepository.delete(motoId);
    }

    public List<Moto> getAll() {
        return motoMapper.toDomains(motoRepository.getAll());
    }

    public Moto get(UUID id) {
        var moto = motoRepository.get(id).orElse(null);
        checkNotFoundWithId(moto, id);
        return motoMapper.toDomain(moto);
    }

    public List<Moto> getByBrand(MotoBrand brand) {
        return motoMapper.toDomains(
                motoRepository.getByBrand(brand)
        );
    }

    public List<Moto> getByType(MotoType type) {
        return motoMapper.toDomains(
                motoRepository.getByType(type)
        );
    }

    public List<Moto> getBetweenCapacity(int fromCapacity, int toCapacity) {
        return motoMapper.toDomains(
                motoRepository.getBetweenCapacity(fromCapacity, toCapacity)
        );
    }
}
