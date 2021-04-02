package br.com.brienze.desafio.dock.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.brienze.desafio.dock.entity.TransacaoEntity;

public interface TransacaoRepository extends CrudRepository<TransacaoEntity, Long>{

}
