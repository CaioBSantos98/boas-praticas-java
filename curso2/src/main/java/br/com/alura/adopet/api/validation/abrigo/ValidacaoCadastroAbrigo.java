package br.com.alura.adopet.api.validation.abrigo;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;

public interface ValidacaoCadastroAbrigo {
    void validar(CadastroAbrigoDto dto);
}
