package br.com.brienze.desafio.dock.adapter;

import java.util.Optional;

import br.com.brienze.desafio.dock.entity.Conta;

public interface ContaPersistenceAdapter {

	Conta save(Conta conta);

	Optional<Conta> busca(Long idConta);

}
