package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.models.District;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository{

    private final Map<String,District> districtPricesSquareMeter = Map.ofEntries(
            Map.entry("BAIRRO DOS ESTADOS", new District("BAIRRO DOS ESTADOS", 450.0)),
            Map.entry("TORRE", new District("TORRE", 300.0)),
            Map.entry("CABO BRANCO", new District("CABO BRANCO", 500.0)),
            Map.entry("MANAIRA", new District("MANAIRA", 700.0)),
            Map.entry("BANACRIOS", new District("BANACRIOS", 400.0))
    );

    public Optional<District> findByName(String name){
        return Optional.ofNullable(districtPricesSquareMeter.get(name));
    }
}
