package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository repository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private CadastroPetDto petDto;

    @Test
    @DisplayName("Metodo save do repository deveria ser chamado")
    void cenario01() {
        // ACT
        service.cadastrarPet(abrigo, petDto);

        // ASSERT
        BDDMockito.then(repository).should().save(new Pet(petDto, abrigo));
    }

}