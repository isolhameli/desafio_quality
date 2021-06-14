package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.exceptions.DistrictNotFoundException;
import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.unit.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public District findByName(String name){
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .strip();
        return districtRepository.findByName(name).orElseThrow(() -> new DistrictNotFoundException("Distrito não encontrado"));
    }
}
