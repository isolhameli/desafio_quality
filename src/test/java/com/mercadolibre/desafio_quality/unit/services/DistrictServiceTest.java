package com.mercadolibre.desafio_quality.unit.services;

import com.mercadolibre.desafio_quality.exceptions.DistrictNotFoundException;
import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.unit.repositories.DistrictRepositoryImpl;
import com.mercadolibre.desafio_quality.services.DistrictServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTest {

    @Mock
    private DistrictRepositoryImpl districtRepository;

    @InjectMocks
    private DistrictServiceImpl districtService;

    private static District district;

    @BeforeAll
    static void init(){
        //given
        district = new District("TEST DISTRICT",10.0);
    }

    @Test
    void testFindByNameReturnsDistrict(){

        //when
        when(districtRepository.findByName(anyString())).thenReturn(Optional.ofNullable(district));

        //assert
        Assertions.assertEquals(district,districtService.findByName("TEST DISTRICT"));
    }

    @Test
    void testFindByNameThrowsExceptionWhenDistrictNotFound(){

        //when
        when(districtRepository.findByName(anyString())).thenReturn(Optional.ofNullable(null));

        //assert
        Exception e = Assertions.assertThrows(DistrictNotFoundException.class,
                () -> districtService.findByName("TEST DISTRICT"));
        Assertions.assertTrue(e.getMessage().contains("Distrito não encontrado"));
    }
}
