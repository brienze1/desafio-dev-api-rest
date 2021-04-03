package br.com.brienze.desafio.dock.adapter;

import br.com.brienze.desafio.dock.entity.Transacao;

public interface TransacaoPersistenceAdapter {

	Transacao save(Transacao transacaoRealizada);

}
