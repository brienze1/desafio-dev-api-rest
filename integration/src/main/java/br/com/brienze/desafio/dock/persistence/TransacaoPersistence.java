package br.com.brienze.desafio.dock.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.TransacaoPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;
import br.com.brienze.desafio.dock.parse.TransacaoEntityParse;
import br.com.brienze.desafio.dock.repository.TransacaoRepository;

@Component
public class TransacaoPersistence implements TransacaoPersistenceAdapter {

	@Autowired
	private TransacaoEntityParse transacaoParse;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Override
	public Transacao save(Transacao transacao) {
		TransacaoEntity transacaoEntity = transacaoParse.toTransacaoEntity(transacao);
		
		TransacaoEntity transacaoEntityCadastrada = transacaoRepository.save(transacaoEntity);
		
		return transacaoParse.toTransacao(transacaoEntityCadastrada);
	}

}
