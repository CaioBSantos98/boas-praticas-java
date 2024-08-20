package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private CadastroAbrigoDto dto;

    @Test
    @DisplayName("Deveria lançar exceção ao tentar cadastrar abrigo já existente!")
    void cenario01 () {
        // ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.email(), dto.telefone())).willReturn(true);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> service.cadatrar(dto));

    }

    @Test
    @DisplayName("Não deveria lançar exceção ao tentar cadastrar abrigo não existente!")
    void cenario02 () {
        // ARRANGE
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.email(), dto.telefone())).willReturn(false);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> service.cadatrar(dto));
        BDDMockito.then(abrigoRepository).should().save(new Abrigo(dto));

    }
}