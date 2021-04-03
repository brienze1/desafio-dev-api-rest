package br.com.brienze.desafio.dock.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.TransacaoPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;
import br.com.brienze.desafio.dock.parse.ContaEntityParse;
import br.com.brienze.desafio.dock.parse.TransacaoEntityParse;
import br.com.brienze.desafio.dock.repository.TransacaoRepository;

@Component
public class TransacaoPersistence implements TransacaoPersistenceAdapter {

	@Autowired
	private TransacaoEntityParse transacaoParse;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private ContaEntityParse contaParse;
	
	@Override
	public Transacao save(Transacao transacao) {
		TransacaoEntity transacaoEntity = transacaoParse.toTransacaoEntity(transacao);
		
		TransacaoEntity transacaoEntityCadastrada = transacaoRepository.save(transacaoEntity);
		
		return transacaoParse.toTransacao(transacaoEntityCadastrada);
	}

	@Override
	public List<Transacao> buscaExtrato(Conta conta, LocalDateTime dataInicio, LocalDateTime dataFim, Integer quantidade, Integer pagina) {
		List<TransacaoEntity> transacoesEntity = transacaoRepository.findByContaAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqualOrderByDataTransacaoDesc(
				contaParse.toContaEntity(conta), 
				dataInicio, 
				dataFim, 
				PageRequest.of(pagina, quantidade));
		
		return transacaoParse.toTransacoes(transacoesEntity);
	}

}
