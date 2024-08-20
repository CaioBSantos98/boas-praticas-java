package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDto;
import br.com.alura.adopet.api.dto.DetalhesAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validation.abrigo.ValidacaoCadastroAbrigo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private List<ValidacaoCadastroAbrigo> validacoesCadastro;

    public void cadastrar(CadastroAbrigoDto dto) {

        validacoesCadastro.forEach(v -> v.validar(dto));

        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());
        repository.save(abrigo);
    }

    public List<DadosDetalhesPetDto> listarPets(String idOuNome) {
        try {
            Abrigo abrigo = this.carregarAbrigo(idOuNome);
            return repository.getReferenceById(abrigo.getId()).getPets().stream().map(DadosDetalhesPetDto::new).toList();
        } catch (ValidacaoException ex) {
            throw new ValidacaoException(ex.getMessage());
        }
    }


    public Page<DetalhesAbrigoDto> listar(Pageable pageable) {
        return repository.findAll(pageable).map(DetalhesAbrigoDto::new);
    }

    public Abrigo carregarAbrigo(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            return abrigo;
        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException(enfe.getMessage());
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                return abrigo;
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException(enfe.getMessage());
            }
        }
    }
}
