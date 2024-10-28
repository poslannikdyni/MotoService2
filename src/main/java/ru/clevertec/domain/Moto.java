package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    private UUID id;
    private MotoBrand brand; // Марка
    private String model; // Модель мотоцикла
    private String vin; // Вин номер мотоцикла
    private MotoType motoType; // Тип мотоцикла
    private LocalDate releaseDate; // Год выпуска
    private long factoryPrice; // Закупочная цена в центах
    private long retailPrice; // Цена на продажу в центах
    private int capacity; // Объём двигателя
    private float power; // Мощность
    private float torque; // Крутящий момент
    private String compression; // Степень сжатия
    private int weight; // Вес
    private float fuelConsumption; // Расход
    private float tankCapacity; // Объём бака
}
