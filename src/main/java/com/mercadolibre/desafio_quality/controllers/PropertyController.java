package com.mercadolibre.desafio_quality.controllers;

import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;
import com.mercadolibre.desafio_quality.services.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/property")
@Validated
@Api(tags = "Property")
public class PropertyController {

    private final PropertyService propertyService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successo"),
            @ApiResponse(code = 400, message = "Requisição Inválida"),
            @ApiResponse(code = 404, message = "Bairro não encontrado")})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "US 0001-0004")
    @PostMapping()
    ResponseEntity<PropertyResponse> propertyDetails(@RequestBody @Valid PropertyRequest propertyRequest){

        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getPropertyInfo(propertyRequest));
    }

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
}
