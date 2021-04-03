package br.com.brienze.desafio.dock.adapter;

import java.time.LocalDateTime;
import java.util.List;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.Transacao;

public interface TransacaoPersistenceAdapter {

	Transacao save(Transacao transacaoRealizada);

	List<Transacao> buscaExtrato(Conta Conta, LocalDateTime dataInicio, LocalDateTime dataFim, Integer quantidade,
			Integer pagina);

}
