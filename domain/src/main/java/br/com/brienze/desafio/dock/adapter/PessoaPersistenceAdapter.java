package br.com.brienze.desafio.dock.adapter;

import java.util.Optional;

import br.com.brienze.desafio.dock.entity.Pessoa;

public interface PessoaPersistenceAdapter {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> busca(Long idPessoa);

}
