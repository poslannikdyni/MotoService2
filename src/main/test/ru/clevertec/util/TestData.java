package ru.clevertec.util;

import org.junit.jupiter.params.provider.Arguments;

import java.util.UUID;
import java.util.stream.Stream;

public class TestData {

    public static Stream<Arguments> valuesForGetNotFoundTest(){
        return Stream.of(
                Arguments.of(UUID.randomUUID()),
                Arguments.of(UUID.randomUUID()),
                Arguments.of(UUID.randomUUID()),
                Arguments.of(UUID.randomUUID()));
    }
}
