package br.com.brienze.desafio.dock.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.adapter.TransacaoPersistenceAdapter;
import br.com.brienze.desafio.dock.builder.TransacaoBuilder;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.rules.TransacaoRules;

@ExtendWith(SpringExtension.class)
public class TransacaoServiceTest {

	@InjectMocks
	private TransacaoService transacaoService;
	
	@Mock
	private TransacaoRules transacaoRules;
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private TransacaoBuilder transacaoBuilder;
	
	@Mock
	private TransacaoPersistenceAdapter transacaoPersistence;
	
	private Transacao transacao;
	private Transacao transacaoRealizada;
	private Transacao transacaoCadastrada;
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacao.setValor(BigDecimal.valueOf(100.00));
		
		transacaoRealizada = new Transacao();

		transacaoCadastrada = new Transacao();
	}
	
	@Test
	public void depositoTest() {
		Mockito.when(transacaoBuilder.buildTransacao(transacao)).thenReturn(transacaoRealizada);
		Mockito.when(transacaoPersistence.save(transacaoRealizada)).thenReturn(transacaoCadastrada);
		
		Transacao transacaoResponse = transacaoService.deposito(transacao);
		
		Mockito.verify(transacaoRules).validateDeposito(transacao);
		Mockito.verify(contaService).transacao(transacao.getValor(), transacao.getIdConta());
		Mockito.verify(transacaoBuilder).buildTransacao(transacao);
		Mockito.verify(transacaoPersistence).save(transacaoRealizada);
		
		Assertions.assertNotNull(transacaoResponse);
	}
	
	@Test
	public void saqueTest() {
		Mockito.when(transacaoBuilder.buildTransacao(transacao)).thenReturn(transacaoRealizada);
		Mockito.when(transacaoPersistence.save(transacaoRealizada)).thenReturn(transacaoCadastrada);
		
		Transacao transacaoResponse = transacaoService.saque(transacao);
		
		Mockito.verify(transacaoRules).validateSaque(transacao);
		Mockito.verify(contaService).transacao(transacao.getValor(), transacao.getIdConta());
		Mockito.verify(transacaoBuilder).buildTransacao(transacao);
		Mockito.verify(transacaoPersistence).save(transacaoRealizada);
		
		Assertions.assertNotNull(transacaoResponse);
	}
	
}
