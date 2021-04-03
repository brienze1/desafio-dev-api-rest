package br.com.brienze.desafio.dock.parse;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;

@Component
public class TransacaoEntityParse {

	public TransacaoEntity toTransacaoEntity(Transacao transacao) {
		TransacaoEntity transacaoEntity = new TransacaoEntity();
		
		if(transacao != null) {
			transacaoEntity.setConta(new ContaEntity());
			transacaoEntity.getConta().setIdConta(transacao.getIdConta());
			transacaoEntity.setDataTransacao(transacao.getDataTransacao());
			transacaoEntity.setIdTransacao(transacao.getIdTransacao());
			transacaoEntity.setValor(transacao.getValor());
		}
		
		return transacaoEntity;
	}

	public Transacao toTransacao(TransacaoEntity transacaoEntity) {
		Transacao transacao = new Transacao();
		
		if(transacaoEntity != null) {
			transacao.setIdConta(transacaoEntity.getConta().getIdConta());
			transacao.setDataTransacao(transacaoEntity.getDataTransacao());
			transacao.setIdTransacao(transacaoEntity.getIdTransacao());
			transacao.setValor(transacaoEntity.getValor());
		}
		
		return transacao;
	}

}
