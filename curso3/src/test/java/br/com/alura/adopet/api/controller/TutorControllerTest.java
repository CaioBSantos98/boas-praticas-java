package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService service;

    @Autowired
    private JacksonTester<CadastroTutorDto> jsonCadastroTutorDto;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> jsonAtualizacaoTutorDto;


    @Test
    @DisplayName("Deveria retornar codigo 400 ao cadastrar tutor invalido")
    void cenario01() throws Exception {
        // ARRANGE
        var json = "{}";

        // ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao cadastrar tutor valido")
    void cenario02() throws Exception {
        // ARRANGE
        var dto = new CadastroTutorDto("Tutor", "35999978889", "tutor@email.com");

        // ACT
        var response = mvc.perform(
                post("/tutores")
                        .content(jsonCadastroTutorDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 400 ao atualizar tutor com informações invalidas")
    void cenario03() throws Exception {
        // ARRANGE
        var json = "{}";

        // ACT
        var response = mvc.perform(
                put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao atualizar tutor com informações validas")
    void cenario04() throws Exception {
        // ARRANGE
        var dto = new AtualizacaoTutorDto(1L, "TutorName", "35999998888", "tutor@email.com");

        // ACT
        var response = mvc.perform(
                put("/tutores")
                        .content(jsonAtualizacaoTutorDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}