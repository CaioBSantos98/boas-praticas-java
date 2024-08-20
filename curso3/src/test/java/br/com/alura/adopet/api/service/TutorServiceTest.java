package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Mock
    private Tutor tutor;

    @Test
    @DisplayName("Deveria lançar exceção ao cadastrar tutor já existente")
    void cenario01() {
        // ARRANGE
        BDDMockito.given(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(true);

        // ACT + ASSERT
        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("Não deveria lançar exceção ao cadastrar tutor ainda não existente")
    void cenario02() {
        // ARRANGE
        BDDMockito.given(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        // ACT + ASSERT
        Assertions.assertDoesNotThrow(() -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("Deveria chamar o metodo save do repository ao cadastrar tutor ainda não existente")
    void cenario03() {
        // ARRANGE
        BDDMockito.given(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        // ACT
        service.cadastrar(dto);

        // ASSERT
        BDDMockito.then(repository).should().save(new Tutor(dto));
    }

    @Test
    @DisplayName("Deveria chamar o atualizarDados do tutor ao atualizar")
    void cenario04() {
        // ARRANGE
        BDDMockito.given(repository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        // ACT
        service.atualizar(atualizacaoTutorDto);

        // ASSERT
        BDDMockito.then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }
}