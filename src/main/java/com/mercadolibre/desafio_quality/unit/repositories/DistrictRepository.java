package com.mercadolibre.desafio_quality.unit.repositories;

import com.mercadolibre.desafio_quality.models.District;

import java.util.Optional;

public interface DistrictRepository {

    Optional<District> findByName(String name);
}
