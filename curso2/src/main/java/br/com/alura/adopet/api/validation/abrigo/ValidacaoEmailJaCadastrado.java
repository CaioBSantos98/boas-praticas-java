package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailJaCadastrado implements ValidacaoCadastroAbrigo{

    @Autowired
    private AbrigoRepository repository;

    public void validar(CadastroAbrigoDto dto) {
        boolean emailJaCadastrado = repository.existsByEmail(dto.email());
        if (emailJaCadastrado) {
            throw new ValidacaoException("Email já cadastrado para outro abrigo!");
        }
    }
}
