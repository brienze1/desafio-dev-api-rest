package br.com.brienze.desafio.dock.parse;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.dto.PessoaDto;
import br.com.brienze.desafio.dock.entity.Pessoa;

@ExtendWith(SpringExtension.class)
public class PessoaParseTest {

	@InjectMocks
	private PessoaParse pessoaParse;

	@Test
	public void toPessoaTest() {
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setCpf(UUID.randomUUID().toString());
		pessoaDto.setDataNascimento("25/01/1995");
		pessoaDto.setIdPessoa(Long.valueOf(12345));
		pessoaDto.setNome(UUID.randomUUID().toString());
		
		Pessoa pessoa = pessoaParse.toPessoa(pessoaDto);
		
		Assertions.assertEquals(pessoaDto.getIdPessoa(), pessoa.getIdPessoa());
		Assertions.assertEquals(pessoaDto.getCpf(), pessoa.getCpf());
		Assertions.assertEquals(pessoaDto.getDataNascimento(), pessoa.getDataNascimento());
		Assertions.assertEquals(pessoaDto.getNome(), pessoa.getNome());
	}
	
	@Test
	public void toPessoaNullTest() {
		Pessoa pessoa = pessoaParse.toPessoa(null);
		
		Assertions.assertNotNull(pessoa);
	}
	
	@Test
	public void toPessoaDtoTest() {
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(UUID.randomUUID().toString());
		pessoa.setDataNascimento("25/01/1995");
		pessoa.setIdPessoa(Long.valueOf(12345));
		pessoa.setNome(UUID.randomUUID().toString());
		
		PessoaDto pessoaDto = pessoaParse.toPessoaDto(pessoa);
		
		Assertions.assertEquals(pessoa.getIdPessoa(), pessoaDto.getIdPessoa());
		Assertions.assertEquals(pessoa.getCpf(), pessoaDto.getCpf());
		Assertions.assertEquals(pessoa.getDataNascimento(), pessoaDto.getDataNascimento());
		Assertions.assertEquals(pessoa.getNome(), pessoaDto.getNome());
	}
	
	@Test
	public void toPessoaDtoNullTest() {
		PessoaDto pessoaDto = pessoaParse.toPessoaDto(null);
		
		Assertions.assertNotNull(pessoaDto);
	}
}
