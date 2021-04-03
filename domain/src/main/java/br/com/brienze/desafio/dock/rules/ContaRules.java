package br.com.brienze.desafio.dock.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.exception.NotFoundException;
import br.com.brienze.desafio.dock.exception.ValidationException;

@Component
public class ContaRules {

	@Autowired
	private PessoaRules pessoaRules;
	
	public boolean validate(Conta conta) {
		if(conta == null) {
			throw new ValidationException("conta nao pode ser nula");
		}
		
		if(conta.getLimiteSaqueDiario() != null && conta.getLimiteSaqueDiario().doubleValue() <= 0) {
			throw new ValidationException("limite_saque_diario nao pode ser menor ou igual a zero");
		}
		
		if(conta.getTipoConta() < 0) {
			throw new ValidationException("tipo_conta nao pode ser menor ou igual a zero");
		}
		
		pessoaRules.validate(conta.getIdPessoa());
		
		return true;
	}

	public boolean validate(Optional<Conta> conta) {
		if(conta.isEmpty()) {
			throw new NotFoundException("id_conta nao cadastrado no sistema");
		}
		
		return true;
	}

}
