package br.com.brienze.desafio.dock.service;

import java.math.BigDecimal;
import java.util.Optional;

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
public class ContaServiceTest {

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
	private Long idConta;
	private BigDecimal valor;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		
		contaCadastrada = new Conta();
		contaCadastrada.setSaldo(BigDecimal.valueOf(1000.00));
		
		idConta = Long.valueOf(123);
		
		valor = BigDecimal.valueOf(100.00);
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
	
	@Test
	public void consultaTest() {
		Mockito.when(contaPersistence.busca(idConta)).thenReturn(Optional.of(contaCadastrada));
		
		Conta contaResponse = contaService.consulta(idConta);
		
		Mockito.verify(contaPersistence).busca(idConta);
		Mockito.verify(contaRules).validate(Optional.of(contaCadastrada));
		
		Assertions.assertNotNull(contaResponse);
	}
	
	@Test
	public void transacaoTest() {
		Mockito.when(contaPersistence.busca(idConta)).thenReturn(Optional.of(contaCadastrada));
		Mockito.when(contaPersistence.save(contaCadastrada)).thenReturn(contaCadastrada);
		
		Conta contaResponse = contaService.transacao(valor, idConta);
		
		Mockito.verify(contaPersistence).busca(idConta);
		Mockito.verify(contaRules).validate(Optional.of(contaCadastrada));
		Mockito.verify(contaPersistence).save(contaCadastrada);
		
		Assertions.assertNotNull(contaResponse);
		Assertions.assertEquals(BigDecimal.valueOf(1100.00), contaCadastrada.getSaldo());
	}
	
	@Test
	public void bloqueiaContaTest() {
		Mockito.when(contaPersistence.busca(idConta)).thenReturn(Optional.of(contaCadastrada));
		Mockito.when(contaPersistence.save(contaCadastrada)).thenReturn(contaCadastrada);
		
		Conta contaResponse = contaService.bloqueiaConta(idConta);
		
		Mockito.verify(contaPersistence).busca(idConta);
		Mockito.verify(contaRules).validate(Optional.of(contaCadastrada));
		Mockito.verify(contaRules).validateBloqueio(contaCadastrada);
		Mockito.verify(contaPersistence).save(contaCadastrada);
		
		Assertions.assertNotNull(contaResponse);
	}
	
}
