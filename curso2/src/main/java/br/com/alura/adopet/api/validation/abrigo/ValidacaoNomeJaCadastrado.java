package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNomeJaCadastrado implements ValidacaoCadastroAbrigo {

    @Autowired
    private AbrigoRepository repository;

    public void validar(CadastroAbrigoDto dto) {
        boolean nomeJaCadastrado = repository.existsByNome(dto.nome());
        if (nomeJaCadastrado) {
            throw new ValidacaoException("Nome já cadastrado para outro abrigo!");
        }
    }
}
