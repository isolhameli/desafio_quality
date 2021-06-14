package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.models.District;

public interface DistrictService {

    District findByName(String name);
}
