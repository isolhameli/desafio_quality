package com.mercadolibre.desafio_quality.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.models.District;
import com.mercadolibre.desafio_quality.models.Room;
import com.mercadolibre.desafio_quality.requests.PropertyRequest;
import com.mercadolibre.desafio_quality.responses.PropertyResponse;
import com.mercadolibre.desafio_quality.services.PropertyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyServiceImpl propertyService;

    @Test
    public void testControllerDTOValidationsAreWorking() throws Exception {
        //given
        String payload = "{\n" +
                "    \"prop_name\":\"minha casaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\n" +
                "    \"prop_district\":\"Bairro dos estádos \",\n" +
                "    \"rooms\": [\n" +
                "        {\n" +
                "            \"room_name\":\"sala\",\n" +
                "            \"room_length\":35\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Quarto\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":40\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Cozinha\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":6.5\n" +
                "        }\n" +
                "    ]\n" +
                "}";



        String expectedResult = "{\n" +
                "    \"message\": \"Erro de validação\",\n" +
                "    \"status\": 400,\n" +
                "    \"errors\": [\n" +
                "        {\n" +
                "            \"fieldName\": \"prop_name\",\n" +
                "            \"errors\": [\n" +
                "                \"O nome da propriedade deve começar com uma letra maiúscula.\",\n" +
                "                \"O comprimento do nome não pode pode exceder 30 caracteres.\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"fieldName\": \"rooms[0].room_width\",\n" +
                "            \"errors\": [\n" +
                "                \"O campo não pode estar vazio\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"fieldName\": \"rooms[0].room_name\",\n" +
                "            \"errors\": [\n" +
                "                \"O nome do cômodo deve começar com uma letra maiúscula.\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"fieldName\": \"rooms[1].room_width\",\n" +
                "            \"errors\": [\n" +
                "                \"A largura máxima permitida por cômodo é de 25 metros\"\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"fieldName\": \"rooms[0].room_length\",\n" +
                "            \"errors\": [\n" +
                "                \"O comprimento máximo permitido por cômodo é de 33 metros\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //then
        mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof MethodArgumentNotValidException))
                .andExpect(content().json(expectedResult));
    }

    @Test
    void testControllerBehavesWellAfterGettingResultFromService() throws Exception {
        //given
        String payload = "{\n" +
                "    \"prop_name\":\"Minha casa\",\n" +
                "    \"prop_district\":\"bairro dos estádos \",\n" +
                "    \"rooms\": [\n" +
                "        {\n" +
                "            \"room_name\":\"Quarto\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":4\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Sala\",\n" +
                "            \"room_length\":10,\n" +
                "            \"room_width\":8\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Cozinha\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":6.5\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        PropertyRequest propertyRequest = new ObjectMapper().readValue(payload, PropertyRequest.class);
        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Quarto",5.0,4.0,20.0),
                new Room("Sala",10.0,8.0,80.0),
                new Room("Cozinha",5.0,6.5,32.5)
        ));
        District district = new District("BAIRRO DOS ESTADOS", 450.0);
        PropertyResponse expectedResponse = new PropertyResponse(propertyRequest,district,132.5,
                59625.0,rooms.get(1),rooms);

        //when
        when(propertyService.getPropertyInfo(any())).thenReturn(expectedResponse);


        //then
        MvcResult result = mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        PropertyResponse actualResponse = new ObjectMapper().readValue(json, PropertyResponse.class);

        Assertions.assertEquals(expectedResponse,actualResponse);

    }

    @Test
    void testControllerHandlesExceptionWhenProvidingWrongDataForRoomsList() throws Exception {
        //given
        String payload = "{\n" +
                "    \"prop_name\":\"Minha casa\",\n" +
                "    \"prop_district\":\"Bairro dos estádos \",\n" +
                "    \"rooms\": 1\n" +
                "}";

        String expectedResult = "{\n" +
                "    \"status\": 400,\n" +
                "    \"message\": \"Valor inválido para o campo rooms\",\n" +
                "    \"params\": [\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_name\",\n" +
                "            \"classType\": \"String\",\n" +
                "            \"required\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_width\",\n" +
                "            \"classType\": \"Double\",\n" +
                "            \"required\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_length\",\n" +
                "            \"classType\": \"Double\",\n" +
                "            \"required\": true\n" +
                "        }\n" +
                "    ]}";

        //then

        mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof HttpMessageNotReadableException))
                .andExpect(content().json(expectedResult)).andReturn();
    }

    @Test
    void testControllerHandlesExceptionWhenProvidingWrongDataForRoomsElement() throws Exception {
        //given
        String payload = "{\n" +
                "    \"prop_name\":\"Minha casa\",\n" +
                "    \"prop_district\":\"Bairro dos estádos \",\n" +
                "    \"rooms\": [1]\n" +
                "}";

        String expectedResult = "{\n" +
                "    \"status\": 400,\n" +
                "    \"message\": \"Valor inválido para o campo rooms[0]\",\n" +
                "    \"params\": [\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_name\",\n" +
                "            \"classType\": \"String\",\n" +
                "            \"required\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_width\",\n" +
                "            \"classType\": \"Double\",\n" +
                "            \"required\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"rooms[0].room_length\",\n" +
                "            \"classType\": \"Double\",\n" +
                "            \"required\": true\n" +
                "        }\n" +
                "    ]}";

        //then
        mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof HttpMessageNotReadableException))
                .andExpect(content().json(expectedResult)).andReturn();
    }

    @Test
    void testControllerHandlesExceptionWhenProvidingInvalidJson() throws Exception {
        //given
        String payload = "{\"";

        String expectedResult = "{\n" +
                "    \"message\": \"Corpo inválido\",\n" +
                "    \"status\": 400}";

        //then
        mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof HttpMessageNotReadableException))
                .andExpect(content().json(expectedResult)).andReturn();
    }

}
