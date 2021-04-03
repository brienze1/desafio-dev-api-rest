package br.com.brienze.desafio.dock.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.ContaPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.parse.ContaEntityParse;
import br.com.brienze.desafio.dock.repository.ContaRepository;

@Component
public class ContaPersistence implements ContaPersistenceAdapter {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ContaEntityParse contaParse;
	
	@Override
	public Conta save(Conta conta) {
		ContaEntity contaEntity = contaParse.toContaEntity(conta);
		
		ContaEntity contaEntityCadastrada = contaRepository.save(contaEntity);
		
		return contaParse.toConta(contaEntityCadastrada);
	}

}
