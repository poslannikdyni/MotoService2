package ru.clevertec.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.clevertec.common.MotoBrand;
import ru.clevertec.common.MotoType;
import ru.clevertec.entity.MotoEntity;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvHelper {
     public static List<MotoEntity> readMoto(String path) throws IOException {
        var brand = "brand";
        var model = "model";
        var vin = "vin";
        var motoType = "motoType";
        var releaseDate = "releaseDate";
        var factoryPrice = "factoryPrice";
        var retailPrice = "retailPrice";
        var capacity = "capacity";
        var power = "power";
        var torque = "torque";
        var compression = "compression";
        var weight = "weight";
        var fuelConsumption = "fuelConsumption";
        var tankCapacity = "tankCapacity";

        String[] HEADERS = {brand, model, vin, motoType,
                releaseDate, factoryPrice, retailPrice, capacity,
                power, torque, compression, weight,
                fuelConsumption, tankCapacity};

        Reader motoCsv = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                .setDelimiter(";")
                .build()
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(motoCsv);

        List<MotoEntity> motoList = new ArrayList<>();
        for (CSVRecord record : records) {
            motoList.add(
                    new MotoEntity(
                            MotoBrand.build(record.get(brand)),
                            record.get(model),
                            record.get(vin),
                            MotoType.build(record.get(motoType)),
                            LocalDate.parse(record.get(releaseDate), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            Long.parseLong(record.get(factoryPrice)),
                            Long.parseLong(record.get(retailPrice)),
                            Integer.parseInt(record.get(capacity)),
                            Float.parseFloat(record.get(power)),
                            Float.parseFloat(record.get(torque)),
                            record.get(compression),
                            Integer.parseInt(record.get(weight)),
                            Float.parseFloat(record.get(fuelConsumption)),
                            Float.parseFloat(record.get(tankCapacity))
                    )
            );
        }
        return motoList;
    }
}
