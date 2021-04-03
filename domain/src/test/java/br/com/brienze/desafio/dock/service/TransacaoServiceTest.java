package br.com.brienze.desafio.dock.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.rules.DateRules;
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
	
	@Mock
	private DateRules dateRules;
	
	private Transacao transacao;
	private Transacao transacaoRealizada;
	private Transacao transacaoCadastrada;
	private Conta conta;
	private Long idConta;
	private String dataInicioString;
	private String dataFimString;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Integer quantidade;
	private Integer pagina;
	private List<Transacao> transacoes; 
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacao.setValor(BigDecimal.valueOf(100.00));
		
		transacaoRealizada = new Transacao();

		transacaoCadastrada = new Transacao();
		
		transacoes = new ArrayList<>();
		transacoes.add(transacao);
		
		conta  = new Conta();
		
		idConta = Long.valueOf(123);
		dataInicioString = "25/01/1995";
		dataFimString = "25/01/1996";
		quantidade = 1;
		pagina = 1;
		dataInicio = LocalDateTime.MAX;
		dataFim = LocalDateTime.MIN;
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
	
	@Test
	public void extratoTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		Mockito.when(contaService.consulta(idConta)).thenReturn(conta);
		Mockito.when(transacaoPersistence.buscaExtrato(conta, dataInicio, dataFim, quantidade, pagina)).thenReturn(transacoes);
		
		List<Transacao> transacoesResponse = transacaoService.consulta(idConta, dataInicioString, dataFimString, quantidade, pagina);
		
		Mockito.verify(transacaoRules).validateParametrosExtrato(idConta, dataInicioString, dataFimString, quantidade, pagina);
		Mockito.verify(dateRules).validate(dataInicioString);
		Mockito.verify(dateRules).validate(dataFimString);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
	@Test
	public void extratoPaginaNulaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		Mockito.when(contaService.consulta(idConta)).thenReturn(conta);
		Mockito.when(transacaoPersistence.buscaExtrato(conta, dataInicio, dataFim, quantidade, 0)).thenReturn(transacoes);
		
		List<Transacao> transacoesResponse = transacaoService.consulta(idConta, dataInicioString, dataFimString, quantidade, null);
		
		Mockito.verify(transacaoRules).validateParametrosExtrato(idConta, dataInicioString, dataFimString, quantidade, null);
		Mockito.verify(dateRules).validate(dataInicioString);
		Mockito.verify(dateRules).validate(dataFimString);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
	@Test
	public void extratoQuantidadeNulaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		Mockito.when(contaService.consulta(idConta)).thenReturn(conta);
		Mockito.when(transacaoPersistence.buscaExtrato(conta, dataInicio, dataFim, 30, pagina)).thenReturn(transacoes);
		
		List<Transacao> transacoesResponse = transacaoService.consulta(idConta, dataInicioString, dataFimString, null, pagina);
		
		Mockito.verify(transacaoRules).validateParametrosExtrato(idConta, dataInicioString, dataFimString, null, pagina);
		Mockito.verify(dateRules).validate(dataInicioString);
		Mockito.verify(dateRules).validate(dataFimString);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
	@Test
	public void extratoDataFimNulaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(contaService.consulta(idConta)).thenReturn(conta);
		Mockito.when(transacaoPersistence.buscaExtrato(conta, dataInicio, LocalDateTime.MIN, quantidade, pagina)).thenReturn(transacoes);
		
		List<Transacao> transacoesResponse = transacaoService.consulta(idConta, dataInicioString, null, quantidade, pagina);
		
		Mockito.verify(transacaoRules).validateParametrosExtrato(idConta, dataInicioString, null, quantidade, pagina);
		Mockito.verify(dateRules).validate(dataInicioString);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
	@Test
	public void extratoDataInicioNulaTest() {
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		Mockito.when(contaService.consulta(idConta)).thenReturn(conta);
		Mockito.when(transacaoPersistence.buscaExtrato(conta, LocalDateTime.MAX, dataFim, quantidade, pagina)).thenReturn(transacoes);
		
		List<Transacao> transacoesResponse = transacaoService.consulta(idConta, null, dataFimString, quantidade, pagina);
		
		Mockito.verify(transacaoRules).validateParametrosExtrato(idConta, null, dataFimString, quantidade, pagina);
		Mockito.verify(dateRules).validate(dataFimString);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
}
