package br.com.brienze.desafio.dock.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.dto.TransacaoDto;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.parse.TransacaoParse;
import br.com.brienze.desafio.dock.service.TransacaoService;

@ExtendWith(SpringExtension.class)
public class TransacoesControllerTest {

	@InjectMocks
	private TransacoesController transacoesController;
	
	@Mock
	private TransacaoParse transacaoParse;
	
	@Mock
	private TransacaoService transacaoService;
	
	private TransacaoDto transacaoDto;
	private Transacao transacao;
	private Transacao transacaoRealizada;
	private TransacaoDto transacaoDtoRealizada;
	private Long idConta;
	private String dataInicio;
	private String dataFim;
	private Integer quantidade;
	private Integer pagina;
	private List<Transacao> transacoes;
	private List<TransacaoDto> transacoesDto;
	
	@BeforeEach
	public void init() {
		transacaoDto = new TransacaoDto();
		
		transacao = new Transacao();

		transacaoRealizada = new Transacao();
		
		transacaoDtoRealizada = new TransacaoDto();
		
		transacoes = new ArrayList<>();

		transacoesDto = new ArrayList<>();
		
		idConta = Long.valueOf(123);
		dataFim = "25/01/1995";
		dataInicio = "25/02/1996";
		quantidade = 1;
		pagina = 1;
	}
	
	@Test
	public void depositoTest() {
		Mockito.when(transacaoParse.toTransacao(transacaoDto)).thenReturn(transacao);
		Mockito.when(transacaoService.deposito(transacao)).thenReturn(transacaoRealizada);
		Mockito.when(transacaoParse.toTransacaoDto(transacaoRealizada)).thenReturn(transacaoDtoRealizada);
		
		ResponseEntity<TransacaoDto> transacaoResponse = transacoesController.deposito(transacaoDto);
		
		Mockito.verify(transacaoParse).toTransacao(transacaoDto);
		Mockito.verify(transacaoService).deposito(transacao);
		Mockito.verify(transacaoParse).toTransacaoDto(transacaoRealizada);
		
		Assertions.assertNotNull(transacaoResponse);
	}
	
	@Test
	public void saqueTest() {
		Mockito.when(transacaoParse.toTransacao(transacaoDto)).thenReturn(transacao);
		Mockito.when(transacaoService.saque(transacao)).thenReturn(transacaoRealizada);
		Mockito.when(transacaoParse.toTransacaoDto(transacaoRealizada)).thenReturn(transacaoDtoRealizada);
		
		ResponseEntity<TransacaoDto> transacaoResponse = transacoesController.saque(transacaoDto);
		
		Mockito.verify(transacaoParse).toTransacao(transacaoDto);
		Mockito.verify(transacaoService).saque(transacao);
		Mockito.verify(transacaoParse).toTransacaoDto(transacaoRealizada);
		
		Assertions.assertNotNull(transacaoResponse);
	}
	
	@Test
	public void extratoTest() {
		Mockito.when(transacaoService.consulta(idConta, dataInicio, dataFim, quantidade, pagina)).thenReturn(transacoes);
		Mockito.when(transacaoParse.toTransacoesDto(transacoes)).thenReturn(transacoesDto);
		
		ResponseEntity<List<TransacaoDto>> transacoesResponse = transacoesController.consultaExtrato(idConta, dataInicio, dataFim, quantidade, pagina);
		
		Mockito.verify(transacaoService).consulta(idConta, dataInicio, dataFim, quantidade, pagina);
		Mockito.verify(transacaoParse).toTransacoesDto(transacoes);
		
		Assertions.assertNotNull(transacoesResponse);
	}
	
}
