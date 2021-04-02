package br.com.brienze.desafio.dock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.PessoaPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.rules.PessoaRules;

@Component
public class PessoaService {

	@Autowired
	private PessoaRules pessoaRules;
	
	@Autowired
	private PessoaPersistenceAdapter pessoaPersistence;
	
	public Pessoa cadastro(Pessoa pessoa) {
		pessoaRules.validate(pessoa);
		
		return pessoaPersistence.save(pessoa);
	}

	public Pessoa busca(Long idPessoa) {
		// TODO Auto-generated method stub
		return null;
	}

}
