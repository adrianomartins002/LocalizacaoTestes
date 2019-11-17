package br.adriano.localizacaoteste.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.adriano.localizacaoteste.domain.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Integer>{

}
