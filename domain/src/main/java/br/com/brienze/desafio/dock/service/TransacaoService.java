package br.com.brienze.desafio.dock.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.adapter.TransacaoPersistenceAdapter;
import br.com.brienze.desafio.dock.builder.TransacaoBuilder;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.rules.DateRules;
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
	
	@Autowired
	private DateRules dateRules;
	
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

	public List<Transacao> consulta(Long idConta, String dataInicioString, String dataFimString, Integer quantidade, Integer pagina) {
		transacaoRules.validateParametrosExtrato(idConta, dataInicioString, dataFimString, quantidade, pagina);

		LocalDateTime dataInicio = LocalDateTime.MAX;
		LocalDateTime dataFim = LocalDateTime.MIN;
		if(pagina == null) {
			pagina = 0;
		}
		if(quantidade == null) {
			quantidade = 30;
		}
		if(dataFimString != null) {
			dataFim = dateRules.validate(dataFimString).withHour(23).withMinute(59).withSecond(59);
		}
		if(dataInicioString != null) {
			dataInicio = dateRules.validate(dataInicioString).withHour(0).withMinute(0).withSecond(0);
		}
		
		return transacaoPersistence.buscaExtrato(contaService.consulta(idConta), dataInicio, dataFim, quantidade, pagina);
	}

}
