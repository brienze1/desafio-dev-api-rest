package br.com.brienze.desafio.dock.parse;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.dto.PessoaDto;
import br.com.brienze.desafio.dock.entity.Pessoa;

@Component
public class PessoaParse {

	public Pessoa toPessoa(PessoaDto pessoaDto) {
		Pessoa pessoa = new Pessoa();
		
		if(pessoaDto != null) {
			pessoa.setCpf(pessoaDto.getCpf());
			pessoa.setDataNascimento(pessoaDto.getDataNascimento());
			pessoa.setIdPessoa(pessoaDto.getIdPessoa());
			pessoa.setNome(pessoaDto.getNome());
		}
		
		return pessoa;
	}

	public PessoaDto toPessoaDto(Pessoa pessoa) {
		PessoaDto pessoaDto = new PessoaDto();
		
		if(pessoa != null) {
			pessoaDto.setCpf(pessoa.getCpf());
			pessoaDto.setDataNascimento(pessoa.getDataNascimento());
			pessoaDto.setIdPessoa(pessoa.getIdPessoa());
			pessoaDto.setNome(pessoa.getNome());
		}
		
		return pessoaDto;
	}

}
