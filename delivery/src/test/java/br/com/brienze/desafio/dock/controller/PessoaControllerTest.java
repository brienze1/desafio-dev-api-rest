package br.com.brienze.desafio.dock.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.dto.PessoaDto;
import br.com.brienze.desafio.dock.endpoint.PessoaController;
import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.parse.PessoaParse;
import br.com.brienze.desafio.dock.service.PessoaService;

@ExtendWith(SpringExtension.class)
public class PessoaControllerTest {
	
	@InjectMocks
	private PessoaController pessoaController;
	
	@Mock
	private PessoaService pessoaService;
	
	@Mock
	private PessoaParse pessoaParse;

	private PessoaDto pessoaDto;
	private Pessoa pessoa;
	private Pessoa pessoaResponse;
	private PessoaDto pessoaDtoResponse;
	
	@BeforeEach
	public void init() {
		pessoaDto = new PessoaDto();
		pessoaDto.setCpf("12345678901");
		pessoaDto.setDataNascimento("25/01/1995");
		pessoaDto.setNome("Jhon Doe");
		
		pessoa = new Pessoa();

		pessoaResponse = new Pessoa();
		
		pessoaDtoResponse = new PessoaDto();
		pessoaDtoResponse.setCpf(pessoaDto.getCpf());
		pessoaDtoResponse.setNome(pessoaDto.getNome());
		pessoaDtoResponse.setDataNascimento(pessoaDto.getDataNascimento());
		pessoaDtoResponse.setIdPessoa(Long.valueOf(123));
		
	}
	
	@Test
	public void cadastroTest() {
		Mockito.when(pessoaParse.toPessoa(pessoaDto)).thenReturn(pessoa);
		Mockito.when(pessoaService.cadastro(pessoa)).thenReturn(pessoaResponse);
		Mockito.when(pessoaParse.toPessoaDto(pessoaResponse)).thenReturn(pessoaDtoResponse);
		
		ResponseEntity<PessoaDto> pessoaDtoResponse = pessoaController.cadastro(pessoaDto);
		
		Mockito.verify(pessoaParse).toPessoa(pessoaDto);
		Mockito.verify(pessoaService).cadastro(pessoa);
		Mockito.verify(pessoaParse).toPessoaDto(pessoaResponse);
		
		Assertions.assertEquals(pessoaDto.getCpf(), pessoaDtoResponse.getBody().getCpf());
		Assertions.assertEquals(pessoaDto.getDataNascimento(), pessoaDtoResponse.getBody().getDataNascimento());
		Assertions.assertEquals(pessoaDto.getNome(), pessoaDtoResponse.getBody().getNome());
		Assertions.assertNotNull(pessoaDtoResponse.getBody().getIdPessoa());
	}
}
