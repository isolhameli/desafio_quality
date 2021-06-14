package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;

public interface PropertyService {

    PropertyResponse getPropertyInfo(PropertyRequest propertyRequest);
    Double calculatePropertyPrice(Double area, Double price);

}
