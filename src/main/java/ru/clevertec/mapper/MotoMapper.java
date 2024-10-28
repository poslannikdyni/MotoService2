package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.clevertec.domain.Moto;
import ru.clevertec.entity.MotoEntity;

import java.util.List;

@Mapper
public interface MotoMapper {
    MotoMapper INSTANCE = Mappers.getMapper(MotoMapper.class);

    List<Moto> toDomains(List<MotoEntity> motoList);

    Moto toDomain(MotoEntity moto);

    MotoEntity toEntity(Moto moto);
}
