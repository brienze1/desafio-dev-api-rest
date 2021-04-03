package br.com.brienze.desafio.dock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.TransacaoPersistenceAdapter;
import br.com.brienze.desafio.dock.builder.TransacaoBuilder;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.rules.TransacaoRules;

@Component
public class TransacaoService {

	@Autowired
	private TransacaoRules transacaoRules;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TransacaoBuilder transacaoBuilder;
	
	@Autowired
	private TransacaoPersistenceAdapter transacaoPersistence;
	
	public Transacao deposito(Transacao transacao) {
		transacaoRules.validateDeposito(transacao);

		return efetivaTransacao(transacao);
	}

	public Transacao saque(Transacao transacao) {
		transacaoRules.validateSaque(transacao);

		transacao.setValor(transacao.getValor().negate());
		
		return efetivaTransacao(transacao);
	}
	
	private Transacao efetivaTransacao(Transacao transacao) {
		contaService.transacao(transacao.getValor(), transacao.getIdConta());
		
		Transacao transacaoRealizada = transacaoBuilder.buildTransacao(transacao);

		return transacaoPersistence.save(transacaoRealizada);
	}

}
