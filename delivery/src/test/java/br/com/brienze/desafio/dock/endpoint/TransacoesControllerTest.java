package br.com.brienze.desafio.dock.endpoint;

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
	
	@BeforeEach
	public void init() {
		transacaoDto = new TransacaoDto();
		
		transacao = new Transacao();

		transacaoRealizada = new Transacao();
		
		transacaoDtoRealizada = new TransacaoDto();
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
	
}
