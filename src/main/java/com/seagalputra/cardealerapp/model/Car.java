package com.seagalputra.cardealerapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Car {
    private Long id;
    private String carId;
    private String color;
    private String vendor;
    private String model;
    private int modelYear;
    private String price;
}
