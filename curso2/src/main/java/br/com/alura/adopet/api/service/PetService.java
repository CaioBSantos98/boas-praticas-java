package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    @Autowired
    private AbrigoService abrigoService;

    public Page<DadosDetalhesPetDto> listarTodosDisponiveis(Pageable pageable) {
        Page<Pet> pets = repository.findByAdotadoFalse(pageable);
        return pets.map(DadosDetalhesPetDto::new);
    }

    public void cadastrarPet(String idOuNome, CadastroPetDto dto) {
        try {
            Abrigo abrigo = abrigoService.carregarAbrigo(idOuNome);
            Pet pet = new Pet(dto, abrigo);
            abrigo.getPets().add(pet);
        } catch (ValidacaoException ex) {
            throw new ValidacaoException(ex.getMessage());
        }
    }
}
