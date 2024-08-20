package br.com.alura.adopet.api.validation.tutor;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTelefoneTutorJaCadastrado implements ValidacaoCadastroTutor{

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(CadastroTutorDto dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());

        if (telefoneJaCadastrado) {
            throw new ValidacaoException("Telefone já cadastrado para outro tutor!");
        }
    }
}
