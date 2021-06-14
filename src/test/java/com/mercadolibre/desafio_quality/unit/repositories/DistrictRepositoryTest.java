package com.mercadolibre.desafio_quality.unit.repositories;

import com.mercadolibre.desafio_quality.models.District;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class DistrictRepositoryTest {

    private static DistrictRepositoryImpl districtRepository = new DistrictRepositoryImpl();
    private static Map<String, District> districtPricesSquareMeter;

    @BeforeAll
    static void init() throws NoSuchFieldException, IllegalAccessException {
        districtPricesSquareMeter = Map.ofEntries(
                Map.entry("BAIRRO DOS ESTADOS", new District("BAIRRO DOS ESTADOS", 450.0)),
                Map.entry("TORRE", new District("TORRE", 300.0)),
                Map.entry("CABO BRANCO", new District("CABO BRANCO", 500.0)),
                Map.entry("MANAIRA", new District("MANAIRA", 700.0)),
                Map.entry("BANACRIOS", new District("BANACRIOS", 400.0))
        );
        Field field = districtRepository.getClass().getDeclaredField("districtPricesSquareMeter");
        field.setAccessible(true);
        field.set(districtRepository, districtPricesSquareMeter);
    }


    @Test
    void testRepositoryReturnsDistrict(){

        //given
        Optional<District> expected = Optional.ofNullable(new District("BAIRRO DOS ESTADOS", 450.0));

        //when
        Optional<District> district = districtRepository.findByName("BAIRRO DOS ESTADOS");

        Assertions.assertEquals(expected,district);
    }

    @Test
    void testRepositoryReturnsNull(){

        //given
        Optional<District> expected = Optional.ofNullable(null);

        //when
        Optional<District> district = districtRepository.findByName("BAIRRO NAO EXISTENTE");

        Assertions.assertEquals(expected,district);
    }
}
