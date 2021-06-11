package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.models.District;

import java.util.Optional;

public interface DistrictService {

    Optional<District> findByName(String name);
}
