package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;

    private SolicitacaoAdocaoDto dto;

    @Spy
    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private Adocao adocao1;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Test
    @DisplayName("Deve lançar exception que o tutor já está com adoção em andamento")
    void cenario01() {
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(1L, 2L, "Motivo qualquer");
        adocoes.add(adocao1);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(adocao1.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao1.getStatus()).willReturn(StatusAdocao.AGUARDANDO_AVALIACAO);

        // ACT + ASSERT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deve lançar exception que o tutor já está com adoção em andamento")
    void cenario02() {
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(1L, 2L, "Motivo qualquer");
        adocoes.add(adocao1);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        BDDMockito.given(adocao1.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao1.getStatus()).willReturn(StatusAdocao.APROVADO);

        // ACT + ASSERT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }
}