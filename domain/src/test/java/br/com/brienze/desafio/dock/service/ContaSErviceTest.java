package br.com.brienze.desafio.dock.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.adapter.ContaPersistenceAdapter;
import br.com.brienze.desafio.dock.builder.ContaBuilder;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.rules.ContaRules;

@ExtendWith(SpringExtension.class)
public class ContaSErviceTest {

	@InjectMocks
	private ContaService contaService;
	
	@Mock
	private ContaRules contaRules;
	
	@Mock
	private ContaPersistenceAdapter contaPersistence;
	
	@Mock
	private ContaBuilder contaBuilder;
	
	private Conta conta;
	private Conta contaNova;
	private Conta contaCadastrada;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		
		contaCadastrada = new Conta();
	}
	
	@Test
	public void cadastroTest() {
		Mockito.when(contaBuilder.buildNovaConta(conta)).thenReturn(contaNova);
		Mockito.when(contaPersistence.save(contaNova)).thenReturn(contaCadastrada);
		
		Conta contaResponse = contaService.cadastro(conta);
		
		Mockito.verify(contaRules).validate(conta);
		Mockito.verify(contaBuilder).buildNovaConta(conta);
		Mockito.verify(contaPersistence).save(contaNova);
		
		Assertions.assertNotNull(contaResponse);
	}
	
}
