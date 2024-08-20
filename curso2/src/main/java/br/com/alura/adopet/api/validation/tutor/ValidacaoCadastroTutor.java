package br.com.alura.adopet.api.validation.tutor;

import br.com.alura.adopet.api.dto.CadastroTutorDto;

public interface ValidacaoCadastroTutor {
    void validar(CadastroTutorDto dto);
}
