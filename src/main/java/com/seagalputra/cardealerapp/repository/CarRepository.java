package com.seagalputra.cardealerapp.repository;

import com.google.gson.Gson;
import com.seagalputra.cardealerapp.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class CarRepository {

    private final List<Car> carDatabase;

    public CarRepository() throws IOException {
        File resource = new ClassPathResource("cars.json").getFile();
        Reader reader = new FileReader(resource);

        Gson gson = new Gson();
        Car[] cars = gson.fromJson(reader, Car[].class);

        this.carDatabase = Arrays.asList(cars);
    }

    public List<Car> getCarDatabase() {
        return this.carDatabase;
    }
}
