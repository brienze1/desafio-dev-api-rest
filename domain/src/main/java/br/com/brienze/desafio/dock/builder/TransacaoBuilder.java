package br.com.brienze.desafio.dock.builder;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Transacao;

@Component
public class TransacaoBuilder {

	public Transacao buildTransacao(Transacao transacao) {
		Transacao transacaoRealizada = new Transacao();
		transacaoRealizada.setDataTransacao(LocalDateTime.now());
		transacaoRealizada.setIdConta(transacao.getIdConta());
		transacaoRealizada.setValor(transacao.getValor());
		
		return transacaoRealizada;
	}

}
