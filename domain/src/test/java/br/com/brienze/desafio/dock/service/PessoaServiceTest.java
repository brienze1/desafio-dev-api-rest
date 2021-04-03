package br.com.brienze.desafio.dock.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.adapter.PessoaPersistenceAdapter;
import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.rules.PessoaRules;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

	@InjectMocks
	private PessoaService pessoaService;
	
	@Mock
	private PessoaRules pessoaRules;
	
	@Mock
	private PessoaPersistenceAdapter pessoaPersistence;
	
	private Pessoa pessoa;
	private Pessoa pessoaSalva;
	private Long idPessoa;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		
		pessoaSalva = new Pessoa();
	
		idPessoa = Long.valueOf(123);
	}
	
	@Test
	public void cadastroTest() {
		Mockito.when(pessoaPersistence.save(pessoa)).thenReturn(pessoaSalva);
		
		Pessoa pessoaResponse = pessoaService.cadastro(pessoa);
		
		Mockito.verify(pessoaRules).validate(pessoa);
		Mockito.verify(pessoaPersistence).save(pessoa);
		
		Assertions.assertNotNull(pessoaResponse);
	}
	
	@Test
	public void buscaTest() {
		Mockito.when(pessoaPersistence.busca(idPessoa)).thenReturn(Optional.of(pessoaSalva));
		
		Optional<Pessoa> pessoaResponse = pessoaService.busca(idPessoa);
		
		Mockito.verify(pessoaPersistence).busca(idPessoa);
		
		Assertions.assertNotNull(pessoaResponse);
		Assertions.assertTrue(pessoaResponse.isPresent());
	}
	
}
