package br.com.brienze.desafio.dock.rules;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.exception.ValidationException;
import br.com.brienze.desafio.dock.service.ContaService;

@Component
public class TransacaoRules {

	@Autowired
	private ContaService contaService;
	
	@Autowired
	private DateRules dateRules;
	
	public boolean validateDeposito(Transacao transacao) {
		validate(transacao);
	
		return true;
	}
	
	public boolean validateSaque(Transacao transacao) {
		validate(transacao);
		
		Conta conta = contaService.consulta(transacao.getIdConta());
		
		if(transacao.getValor().compareTo(conta.getSaldo()) >= 1) {
			throw new ValidationException("saldo insuficiente");
		}
		
		return true;
	}

	private void validate(Transacao transacao) {
		if(transacao == null) {
			throw new ValidationException("transacao nao pode ser nula");
		}
		
		if(transacao.getIdConta() == null) {
			throw new ValidationException("id_conta nao pode ser nulo");
		}
		
		Conta conta = contaService.consulta(transacao.getIdConta());

		if(!conta.getFlagAtivo()) {
			throw new ValidationException("transacao recusada, conta bloqueada");
		}
		
		if(transacao.getValor() == null) {
			throw new ValidationException("valor nao pode ser nulo");
		}
		
		if(transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("valor nao pode ser menor ou igual a zero");
		}
		
	}

	public boolean validateParametrosExtrato(Long idConta, String dataFimString, String dataInicioString, Integer quantidade, Integer pagina) {
		if(idConta == null) {
			throw new ValidationException("id_conta nao pode ser nulo");
		}
		
		contaService.consulta(idConta);
		
		if(dataFimString != null && dataInicioString != null && dateRules.validate(dataInicioString).isBefore(dateRules.validate(dataFimString))) {
			throw new ValidationException("data_inicio deve vir antes da data_fim");
		}
		
		if(pagina != null && pagina < 0) {
			throw new ValidationException("pagina deve ser maior ou igual a zero");
		}

		if(quantidade != null && quantidade < 0) {
			throw new ValidationException("quantidade deve ser maior ou igual a zero");
		}
		
		return true;
	}

}
