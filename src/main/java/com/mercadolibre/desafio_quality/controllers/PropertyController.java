package com.mercadolibre.desafio_quality.controllers;

import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;
import com.mercadolibre.desafio_quality.services.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/property")
@Validated
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping()
    ResponseEntity<PropertyResponse> propertyDetails(@RequestBody @Valid PropertyRequest propertyRequest){

        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getPropertyInfo(propertyRequest));
    }
}
