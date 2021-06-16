package com.gabrielSouza.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielSouza.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
