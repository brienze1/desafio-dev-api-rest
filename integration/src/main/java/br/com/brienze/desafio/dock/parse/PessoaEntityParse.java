package br.com.brienze.desafio.dock.parse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.entity.PessoaEntity;

@Component
public class PessoaEntityParse {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public PessoaEntity toPessoaEntity(Pessoa pessoa) {
		PessoaEntity pessoaEntity = new  PessoaEntity();
		
		if(pessoa != null) {
			pessoaEntity.setCpf(pessoa.getCpf());
			pessoaEntity.setIdPessoa(pessoa.getIdPessoa());
			pessoaEntity.setNome(pessoa.getNome());
			pessoaEntity.setDataNascimento(LocalDate.parse(pessoa.getDataNascimento(), FORMAT));
		}

		return pessoaEntity;
	}

	public Pessoa toPessoa(PessoaEntity pessoaEntity) {
		Pessoa pessoa = new  Pessoa();
		
		if(pessoaEntity != null) {
			pessoa.setCpf(pessoaEntity.getCpf());
			pessoa.setIdPessoa(pessoaEntity.getIdPessoa());
			pessoa.setNome(pessoaEntity.getNome());
			pessoa.setDataNascimento(pessoaEntity.getDataNascimento().format(FORMAT));
		}

		return pessoa;
	}

	public Optional<Pessoa> toPessoa(Optional<PessoaEntity> pessoaEntity) {
		if(pessoaEntity.isPresent()) {
			return Optional.of(toPessoa(pessoaEntity.get()));
		}
		
		return Optional.ofNullable(null);
	}

}
