package br.com.brienze.desafio.dock.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.exception.ValidationException;

@Component
public class PessoaRules {

	@Autowired
	private DateRules daterules;
	
	public boolean validate(Pessoa pessoa) {
		if(pessoa == null) {
			throw new ValidationException("objeto pessoa nao pode ser nulo");
		}

		if(pessoa.getNome() == null || pessoa.getNome().isBlank()) {
			throw new ValidationException("nome nao pode ser nulo ou em branco");
		}
		
		if(pessoa.getCpf() == null || pessoa.getCpf().isBlank()) {
			throw new ValidationException("cpf nao pode ser nulo ou em branco");
		}
		
		if(pessoa.getCpf().length() != 11) {
			throw new ValidationException("cpf deve conter 11 caracteres");
		}
		
		if(pessoa.getDataNascimento() == null || pessoa.getDataNascimento().isBlank()) {
			throw new ValidationException("data_nascimento nao pode ser nula ou em branco");
		}
		
		daterules.validate(pessoa.getDataNascimento());
		
		return true;
		
	}

}
