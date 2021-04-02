package br.com.brienze.desafio.dock.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.brienze.desafio.dock.entity.PessoaEntity;

public interface PessoaRepository extends CrudRepository<PessoaEntity, Long> {

}