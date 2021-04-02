package br.com.brienze.desafio.dock.service;

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
	private PessoaPersistenceAdapter pessoaPesistence;
	
	private Pessoa pessoa;
	private Pessoa pessoaSalva;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		
		pessoaSalva = new Pessoa();
	}
	
	@Test
	public void Test() {
		Mockito.when(pessoaPesistence.save(pessoa)).thenReturn(pessoaSalva);
		
		Pessoa pessoaResponse = pessoaService.cadastro(pessoa);
		
		Mockito.verify(pessoaRules).validate(pessoa);
		Mockito.verify(pessoaPesistence).save(pessoa);
		
		Assertions.assertNotNull(pessoaResponse);
	}
	
}
