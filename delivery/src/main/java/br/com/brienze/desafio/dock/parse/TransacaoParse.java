package br.com.brienze.desafio.dock.parse;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.dto.TransacaoDto;
import br.com.brienze.desafio.dock.entity.Transacao;

@Component
public class TransacaoParse {

	public Transacao toTransacao(TransacaoDto transacaoDto) {
		Transacao transacao = new Transacao();
		
		if(transacaoDto != null) {
			transacao.setDataTransacao(transacaoDto.getDataTransacao());
			transacao.setIdConta(transacaoDto.getIdConta());
			transacao.setIdTransacao(transacaoDto.getIdTransacao());
			transacao.setValor(transacaoDto.getValor());
		}
		
		return transacao;
	}

	public TransacaoDto toTransacaoDto(Transacao transacao) {
		TransacaoDto transacaoDto = new TransacaoDto();
		
		if(transacao != null) {
			transacaoDto.setDataTransacao(transacao.getDataTransacao());
			transacaoDto.setIdConta(transacao.getIdConta());
			transacaoDto.setIdTransacao(transacao.getIdTransacao());
			transacaoDto.setValor(transacao.getValor());
		}
		
		return transacaoDto;
	}

	
	
}
