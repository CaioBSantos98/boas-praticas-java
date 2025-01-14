package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
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
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Test
    @DisplayName("Deveria lançar exception que o pet já tem uma adoção em andamento")
    void cenario01() {
        // ARRANGE
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria lançar exception que o pet já tem uma adoção em andamento")
    void cenario02() {
        // ARRANGE
        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }
}