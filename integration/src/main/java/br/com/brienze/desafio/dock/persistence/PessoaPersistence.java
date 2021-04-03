package br.com.brienze.desafio.dock.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.PessoaPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.entity.PessoaEntity;
import br.com.brienze.desafio.dock.parse.PessoaEntityParse;
import br.com.brienze.desafio.dock.repository.PessoaRepository;

@Component
public class PessoaPersistence implements PessoaPersistenceAdapter {

	@Autowired
	private PessoaEntityParse pessoaParse;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public Pessoa save(Pessoa pessoa) {
		PessoaEntity pessoaEntity = pessoaParse.toPessoaEntity(pessoa);
		
		PessoaEntity pessoaEntitySaved = pessoaRepository.save(pessoaEntity);
		
		return pessoaParse.toPessoa(pessoaEntitySaved);
	}

	@Override
	public Optional<Pessoa> busca(Long idPessoa) {
		Optional<PessoaEntity> pessoaEntitySaved = pessoaRepository.findById(idPessoa);
		
		return pessoaParse.toPessoa(pessoaEntitySaved);
	}

}
