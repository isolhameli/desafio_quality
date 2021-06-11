package com.mercadolibre.desafio_quality.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRightOutput() throws Exception {

        String payload = "{\n" +
                "    \"prop_name\":\"Minha casa\",\n" +
                "    \"prop_district\":\"bairro dos estádos \",\n" +
                "    \"rooms\": [\n" +
                "        {\n" +
                "            \"room_name\":\"Sala\",\n" +
                "            \"room_length\":10,\n" +
                "            \"room_width\":8\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Quarto\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":4\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\":\"Cozinha\",\n" +
                "            \"room_length\":5,\n" +
                "            \"room_width\":6.5\n" +
                "        }\n" +
                "    ]\n" +
                "}";



        String expectedResult = "{\n" +
                "    \"prop_name\": \"Minha casa\",\n" +
                "    \"prop_district\": \"BAIRRO DOS ESTADOS\",\n" +
                "    \"total_area_m2\": 132.5,\n" +
                "    \"prop_value\": 59625.0,\n" +
                "    \"largest_room\": {\n" +
                "        \"room_name\": \"Sala\",\n" +
                "        \"area_m2\": 80.0\n" +
                "    },\n" +
                "    \"rooms\": [\n" +
                "        {\n" +
                "            \"room_name\": \"Sala\",\n" +
                "            \"area_m2\": 80.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\": \"Quarto\",\n" +
                "            \"area_m2\": 20.0\n" +
                "        },\n" +
                "        {\n" +
                "            \"room_name\": \"Cozinha\",\n" +
                "            \"area_m2\": 32.5\n" +
                "        }\n" +
                "    ]"+
                "}";

        mockMvc.perform(
                post("/property")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().json(expectedResult));

    }

}
