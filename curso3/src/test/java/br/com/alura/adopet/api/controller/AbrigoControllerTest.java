package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<List<AbrigoDto>> jsonAbrigoDto;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonCadastroAbrigoDto;

    @Autowired
    private JacksonTester<List<PetDto>> jsonListPetDto;

    @Autowired
    private JacksonTester<CadastroPetDto> jsonPetDto;

    private List<AbrigoDto> abrigos = new ArrayList<>();

    private List<PetDto> pets = new ArrayList<>();

    @Test
    @DisplayName("Deveria retornar codigo 200 ao listar abrigos")
    void cenario01() throws Exception {
        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos")
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia caso não tenha abrigos cadastrados")
    void cenario02() throws Exception {
        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos")
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals("[]", response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar uma lista de abrigos caso tenha abrigos cadastrados")
    void cenario03() throws Exception {
        // ARRANGE
        abrigos.add(new AbrigoDto(1L, "Teste 1"));
        abrigos.add(new AbrigoDto(2L, "Teste 2"));
        given(abrigoService.listar()).willReturn(abrigos);

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos")
        ).andReturn().getResponse();

        // ASSERT
        var abrigosConvertidoParaJson = jsonAbrigoDto.write(abrigos).getJson();
        Assertions.assertEquals(abrigosConvertidoParaJson, response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao cadastrar abrigo valido")
    void cenario04() throws Exception {
        // ARRANGE
        var json = new CadastroAbrigoDto("Abrigo teste","35999975566", "email@teste.com");

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .content(jsonCadastroAbrigoDto.write(json).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 400 ao cadastrar abrigo invalido")
    void cenario05() throws Exception {
        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao listar pets de abrigo não encontrado")
    void cenario06() throws Exception {
        // ARRANGE
        given(abrigoService.listarPetsDoAbrigo("teste")).willThrow(new ValidacaoException("Abrigo não encontrado"));

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/teste/pets")
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao listar pets de abrigo encontrado")
    void cenario07() throws Exception {
        // ARRANGE
        given(abrigoService.listarPetsDoAbrigo("teste")).willReturn(pets);

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/teste/pets")
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(jsonListPetDto.write(pets).getJson(), response.getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar codigo 404 ao cadastrar pet de abrigo não encontrado")
    void cenario08() throws Exception {
        // ARRANGE
        var petDto = new CadastroPetDto(TipoPet.CACHORRO, "Cao", "Pastor", 5, "branco", 5.0F);
        given(abrigoService.carregarAbrigo("teste")).willThrow(new ValidacaoException("Abrigo não encontrado"));

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos/teste/pets")
                        .content(jsonPetDto.write(petDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deveria retornar codigo 200 ao cadastrar pet de abrigo encontrado")
    void cenario09() throws Exception {
        // ARRANGE
        var petDto = new CadastroPetDto(TipoPet.CACHORRO, "Cao", "Pastor", 5, "branco", 5.0F);

        // ACT
        var response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos/teste/pets")
                        .content(jsonPetDto.write(petDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

}