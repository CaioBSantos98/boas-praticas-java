package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizaTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validation.tutor.ValidacaoCadastroTutor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    private List<ValidacaoCadastroTutor> validacoesCadastro;

    public void cadastrar(CadastroTutorDto dto) {

        validacoesCadastro.forEach(v -> v.validar(dto));

        Tutor tutor = new Tutor(dto);
        repository.save(tutor);

    }

    public void atualizar(AtualizaTutorDto dto) {
        try {
            Tutor tutor = repository.getReferenceById(dto.id());
            tutor.atualizarDados(dto);
        } catch (EntityNotFoundException ex) {
            throw new ValidacaoException("Tutor não encontrado!");
        }
    }
}
