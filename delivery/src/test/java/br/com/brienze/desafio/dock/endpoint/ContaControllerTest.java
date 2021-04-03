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

import br.com.brienze.desafio.dock.dto.ContaDto;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.parse.ContaParse;
import br.com.brienze.desafio.dock.service.ContaService;

@ExtendWith(SpringExtension.class)
public class ContaControllerTest {

	@InjectMocks
	private ContaController contaController;
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private ContaParse contaParse;

	private ContaDto contaDto;
	private Conta conta;
	private Conta contaResponse;
	private ContaDto contaDtoResponse;
	private Long idConta;
	
	@BeforeEach
	public void init() {
		contaDto = new ContaDto();

		conta = new Conta();
		
		contaResponse = new Conta();
		
		contaDtoResponse = new ContaDto();
		
		idConta = Long.valueOf(123);
	}
	
	@Test
	public void cadastroTest() {
		Mockito.when(contaParse.toConta(contaDto)).thenReturn(conta);
		Mockito.when(contaService.cadastro(conta)).thenReturn(contaResponse);
		Mockito.when(contaParse.toContaDto(contaResponse)).thenReturn(contaDtoResponse);
		
		ResponseEntity<ContaDto> contaDtoResponse = contaController.cadastro(contaDto);
		
		Mockito.verify(contaParse).toConta(contaDto);
		Mockito.verify(contaService).cadastro(conta);
		Mockito.verify(contaParse).toContaDto(contaResponse);
		
		Assertions.assertNotNull(contaDtoResponse.getBody());
	}
	
	@Test
	public void consultaSaldoTest() {
		Mockito.when(contaService.consulta(idConta)).thenReturn(contaResponse);
		Mockito.when(contaParse.toContaDto(contaResponse)).thenReturn(contaDtoResponse);
		
		ResponseEntity<ContaDto> contaDtoResponse = contaController.consultaSaldo(idConta);
		
		Mockito.verify(contaService).consulta(idConta);
		Mockito.verify(contaParse).toContaDto(contaResponse);
		
		Assertions.assertNotNull(contaDtoResponse.getBody());
	}
	
	@Test
	public void bloqueioTest() {
		Mockito.when(contaService.bloqueiaConta(idConta)).thenReturn(contaResponse);
		Mockito.when(contaParse.toContaDto(contaResponse)).thenReturn(contaDtoResponse);
		
		ResponseEntity<ContaDto> contaDtoResponse = contaController.bloqueiaConta(idConta);
		
		Mockito.verify(contaService).bloqueiaConta(idConta);
		Mockito.verify(contaParse).toContaDto(contaResponse);
		
		Assertions.assertNotNull(contaDtoResponse.getBody());
	}
	
}
