package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.repositories.DistrictRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public Optional<District> findByName(String name){
        name = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .strip();
        return districtRepository.findByName(name);
    }
}
