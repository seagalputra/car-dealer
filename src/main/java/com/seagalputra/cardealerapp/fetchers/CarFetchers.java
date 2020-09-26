package com.seagalputra.cardealerapp.fetchers;

import com.seagalputra.cardealerapp.model.Car;
import com.seagalputra.cardealerapp.repository.CarRepository;
import graphql.schema.DataFetcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CarFetchers {

    private final CarRepository carRepository;

    public DataFetcher<Car> getCarByIdFetcher() {
        return dataFetchingEnvironment -> {
            String carId = dataFetchingEnvironment.getArgument("id");
            return carRepository.getCarDatabase().stream()
                    .filter(car -> String.valueOf(car.getId()).equals(carId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Car>> getAllCar() {
        return dataFetchingEnvironment -> {
            int maxSize = dataFetchingEnvironment.getArgument("limit");
            return carRepository.getCarDatabase().stream()
                    .limit(maxSize)
                    .collect(Collectors.toList());
        };
    }
}
