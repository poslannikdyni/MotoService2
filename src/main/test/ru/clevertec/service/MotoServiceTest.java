package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;
import ru.clevertec.domain.Moto;
import ru.clevertec.entity.MotoEntity;
import ru.clevertec.exception.NotFoundException;
import ru.clevertec.mapper.MotoMapper;
import ru.clevertec.repository.MotoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;

    @Mock
    private MotoMapper motoMapper;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @InjectMocks
    private MotoService motoService;

    @Test
    void shouldGetAllMoto() {
        // given
        List<MotoEntity> motoEntities = List.of(new MotoEntity(), new MotoEntity());
        List<Moto> motoList = List.of(new Moto(), new Moto());

        when(motoRepository.getAll())
                .thenReturn(motoEntities);
        when(motoMapper.toDomains(motoEntities))
                .thenReturn(motoList);

        // when
        List<Moto> actualMotoList = motoService.getAll();

        // then
        assertEquals(motoList, actualMotoList);
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldGetMotoById() {
        // given
        UUID motoId = UUID.randomUUID();
        MotoEntity motoEntity = new MotoEntity();
        motoEntity.setId(motoId);

        Moto moto = new Moto();
        moto.setId(motoId);

        when(motoRepository.get(motoId))
                .thenReturn(Optional.of(motoEntity));
        when(motoMapper.toDomain(motoEntity))
                .thenReturn(moto);

        // when
        Moto actualMoto = motoService.get(motoId);

        // then
        assertEquals(moto.getId(), actualMoto.getId());
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldGetMotoByBrand() {
        // given
        MotoBrand brand = MotoBrand.APRILIA;
        List<MotoEntity> motoEntities = List.of(new MotoEntity(), new MotoEntity(), new MotoEntity(), new MotoEntity());
        List<Moto> motoList = List.of(new Moto(), new Moto(), new Moto(), new Moto());

        when(motoRepository.getByBrand(brand))
                .thenReturn(motoEntities);
        when(motoMapper.toDomains(motoEntities))
                .thenReturn(motoList);

        // when
        List<Moto> actualMotoList = motoService.getByBrand(brand);

        // then
        assertEquals(motoList, actualMotoList);
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldGetMotoByType() {
        // given
        MotoType type = MotoType.CLASSIC;
        List<MotoEntity> motoEntities = List.of(new MotoEntity(), new MotoEntity(), new MotoEntity(), new MotoEntity());
        List<Moto> motoList = List.of(new Moto(), new Moto(), new Moto(), new Moto());

        when(motoRepository.getByType(type))
                .thenReturn(motoEntities);
        when(motoMapper.toDomains(motoEntities))
                .thenReturn(motoList);

        // when
        List<Moto> actualMotoList = motoService.getByType(type);

        // then
        assertEquals(motoList, actualMotoList);
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldGetMotoByCapacity() {
        // given
        List<MotoEntity> motoEntities = List.of(new MotoEntity(), new MotoEntity(), new MotoEntity(), new MotoEntity());
        List<Moto> motoList = List.of(new Moto(), new Moto(), new Moto(), new Moto());

        when(motoRepository.getBetweenCapacity(300, 700))
                .thenReturn(motoEntities);
        when(motoMapper.toDomains(motoEntities))
                .thenReturn(motoList);

        // when
        List<Moto> actualMotoList = motoService.getBetweenCapacity(300, 700);

        // then
        assertEquals(motoList, actualMotoList);
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldNotFoundException_whenMotoNotWithIdNotFound() {
        // given
        UUID motoId = UUID.randomUUID();

        when(motoRepository.get(motoId))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(
                NotFoundException.class,
                () -> motoService.get(motoId)
        );
        verifyNoMoreInteractions(motoMapper);
        verifyNoMoreInteractions(motoRepository);
    }

    @Test
    void shouldDeleteById() {
        // given
        UUID motoId = UUID.randomUUID();

        // when
        motoService.delete(motoId);

        // then
        verify(motoRepository).delete(uuidCaptor.capture());
        assertEquals(motoId, uuidCaptor.getValue());

        verifyNoInteractions(motoMapper);
        verify(motoRepository, never()).getAll();
    }

    @Test
    void shouldCreate(){
        // given
        UUID motoId = UUID.randomUUID();
        Moto moto = new Moto();
        Moto expectedMoto = new Moto();
        expectedMoto.setId(motoId);
        MotoEntity mappingEntity = new MotoEntity();
        MotoEntity entityAfterCreate = new MotoEntity();
        when(motoMapper.toEntity(moto))
                .thenReturn(mappingEntity);
        when(motoRepository.save(mappingEntity))
                .thenReturn(entityAfterCreate);
        when(motoMapper.toDomain(entityAfterCreate))
                .thenReturn(expectedMoto);

        // when
        Moto actualMoto = motoService.create(moto);

        // then
        assertNotNull(actualMoto.getId());
        assertEquals(expectedMoto.getId(), actualMoto.getId());
        verify(motoRepository).save(mappingEntity);
        verifyNoMoreInteractions(motoRepository);
        verifyNoMoreInteractions(motoMapper);
    }

    @Test
    void shouldUpdate(){
        // given
        UUID updateMotoId = UUID.randomUUID();
        Moto moto = new Moto();
        MotoEntity forUpdateMoto = new MotoEntity();
        MotoEntity afterUpdateMoto = new MotoEntity();
        Moto expectedMoto = new Moto();
        when(motoMapper.toEntity(moto))
                .thenReturn(forUpdateMoto);
        when(motoRepository.save(forUpdateMoto))
                .thenReturn(afterUpdateMoto);
        when(motoMapper.toDomain(afterUpdateMoto))
                .thenReturn(expectedMoto);

        // when
        Moto actualMoto = motoService.update(updateMotoId, moto);

        // then
        verify(motoRepository).save(forUpdateMoto);

        assertEquals(expectedMoto, actualMoto);

        verifyNoMoreInteractions(motoRepository);
        verifyNoMoreInteractions(motoMapper);
    }

    @ParameterizedTest
    @MethodSource("ru.clevertec.util.TestData#valuesForGetNotFoundTest")
    void shouldNotFoundException_whenGetWithNoValidId(UUID id) {
        // when, then
        assertThrows(
                NotFoundException.class,
                () -> motoService.get(id)
        );
    }
}
