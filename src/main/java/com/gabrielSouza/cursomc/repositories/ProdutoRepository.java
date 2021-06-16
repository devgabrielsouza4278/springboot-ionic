package com.gabrielSouza.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielSouza.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
