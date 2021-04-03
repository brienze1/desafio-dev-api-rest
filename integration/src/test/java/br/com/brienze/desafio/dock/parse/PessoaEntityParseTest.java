package br.com.brienze.desafio.dock.parse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.entity.PessoaEntity;

@ExtendWith(SpringExtension.class)
public class PessoaEntityParseTest {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@InjectMocks
	private PessoaEntityParse pessoaParse;
	
	private Pessoa pessoa;
	private PessoaEntity pessoaEntity;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		pessoa.setCpf("12345678901");
		pessoa.setDataNascimento("25/01/1995");
		pessoa.setIdPessoa(Long.valueOf(123));
		pessoa.setNome(UUID.randomUUID().toString());

		pessoaEntity = new PessoaEntity();
		pessoaEntity.setCpf("12345678901");
		pessoaEntity.setDataNascimento(LocalDate.of(1995, 1, 25));
		pessoaEntity.setIdPessoa(Long.valueOf(123));
		pessoaEntity.setNome(UUID.randomUUID().toString());
	}
	
	
	@Test
	public void toPessoaTest() {
		Pessoa pessoa = pessoaParse.toPessoa(pessoaEntity);
		
		Assertions.assertEquals(pessoaEntity.getIdPessoa(), pessoa.getIdPessoa());
		Assertions.assertEquals(pessoaEntity.getCpf(), pessoa.getCpf());
		Assertions.assertEquals(pessoaEntity.getDataNascimento().format(FORMAT), pessoa.getDataNascimento());
		Assertions.assertEquals(pessoaEntity.getNome(), pessoa.getNome());
	}
	
	@Test
	public void toPessoaNullTest() {
		pessoaEntity = null;
		
		Pessoa pessoa = pessoaParse.toPessoa(pessoaEntity);
		
		Assertions.assertNotNull(pessoa);
	}
	
	@Test
	public void toPessoaEntityNullTest() {
		PessoaEntity pessoaEntity = pessoaParse.toPessoaEntity(null);
		
		Assertions.assertNotNull(pessoaEntity);
	}
	
	@Test
	public void toPessoaEntityTest() {
		PessoaEntity pessoaEntity = pessoaParse.toPessoaEntity(pessoa);
		
		Assertions.assertEquals(pessoa.getIdPessoa(), pessoaEntity.getIdPessoa());
		Assertions.assertEquals(pessoa.getCpf(), pessoaEntity.getCpf());
		Assertions.assertEquals(pessoa.getDataNascimento(), pessoaEntity.getDataNascimento().format(FORMAT));
		Assertions.assertEquals(pessoa.getNome(), pessoaEntity.getNome());
	}
	
	@Test
	public void toPessoaOptionalTest() {
		Optional<Pessoa> pessoa = pessoaParse.toPessoa(Optional.of(pessoaEntity));
		
		
		Assertions.assertNotNull(pessoa);
		Assertions.assertTrue(pessoa.isPresent());
		Assertions.assertEquals(pessoaEntity.getIdPessoa(), pessoa.get().getIdPessoa());
		Assertions.assertEquals(pessoaEntity.getCpf(), pessoa.get().getCpf());
		Assertions.assertEquals(pessoaEntity.getDataNascimento().format(FORMAT), pessoa.get().getDataNascimento());
		Assertions.assertEquals(pessoaEntity.getNome(), pessoa.get().getNome());
	}
	
	@Test
	public void toPessoaNotpresentOptionalTest() {
		Optional<Pessoa> pessoa = pessoaParse.toPessoa(Optional.ofNullable(null));
		
		Assertions.assertNotNull(pessoa);
		Assertions.assertFalse(pessoa.isPresent());
	}
	
}
