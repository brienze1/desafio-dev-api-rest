package br.com.brienze.desafio.dock.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.ContaPersistenceAdapter;
import br.com.brienze.desafio.dock.builder.ContaBuilder;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.rules.ContaRules;

@Component
public class ContaService {

	@Autowired
	private ContaRules contaRules;
	
	@Autowired
	private ContaBuilder contaBuilder;
	
	@Autowired
	private ContaPersistenceAdapter contaPersistence;
	
	public Conta cadastro(Conta conta) {
		contaRules.validate(conta);
		
		Conta contaNova = contaBuilder.buildNovaConta(conta);
		
		return contaPersistence.save(contaNova);
	}

	public Conta consulta(Long idConta) {
		Optional<Conta> conta = contaPersistence.busca(idConta);
		
		contaRules.validate(conta);
		
		return conta.get();
	}
	
	public Conta transacao(BigDecimal valor, Long idConta) {
		Conta conta = consulta(idConta);
		
		conta.setSaldo(conta.getSaldo().add(valor));
		
		return contaPersistence.save(conta);
	}

}
