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
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Spy
    private List<Adocao> adocoes = new ArrayList<>();

    @Mock
    private Adocao adocao1;

    @Mock
    private Adocao adocao2;

    @Mock
    private Adocao adocao3;

    @Mock
    private Adocao adocao4;

    @Mock
    private Adocao adocao5;

    private SolicitacaoAdocaoDto dto;

    @Test
    @DisplayName("Deveria lançar uma exception que o tutor chegou ao limite máximo de 5 adoções")
    void cenario01() {
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(1L, 2L, "Motivo qualquer");
        adocoes.add(adocao1);
        adocoes.add(adocao2);
        adocoes.add(adocao3);
        adocoes.add(adocao4);
        adocoes.add(adocao5);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        for (Adocao a : adocoes) {
            BDDMockito.given(a.getTutor()).willReturn(tutor);
            BDDMockito.given(a.getStatus()).willReturn(StatusAdocao.APROVADO);
        }

        // ACT + ASSERT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria lançar uma exception que o tutor tem menos de 5 adoções aprovadas")
    void cenario02() {
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(1L, 2L, "Motivo qualquer");
        adocoes.add(adocao1);
        adocoes.add(adocao2);
        adocoes.add(adocao3);
        adocoes.add(adocao4);
        BDDMockito.given(adocaoRepository.findAll()).willReturn(adocoes);
        BDDMockito.given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        for (Adocao a : adocoes) {
            BDDMockito.given(a.getTutor()).willReturn(tutor);
            BDDMockito.given(a.getStatus()).willReturn(StatusAdocao.APROVADO);
        }

        // ACT + ASSERT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }


}