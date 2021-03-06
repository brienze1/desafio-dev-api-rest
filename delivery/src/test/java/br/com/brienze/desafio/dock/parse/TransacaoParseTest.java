package br.com.brienze.desafio.dock.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.dto.TransacaoDto;
import br.com.brienze.desafio.dock.entity.Transacao;

@ExtendWith(SpringExtension.class)
public class TransacaoParseTest {

	@InjectMocks
	private TransacaoParse transacaoParse;
	
	private Transacao transacao;
	private TransacaoDto transacaoDto;
	private List<Transacao> transacoes;

	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacao.setDataTransacao(LocalDateTime.now());
		transacao.setIdConta(Long.valueOf(123));
		transacao.setIdTransacao(Long.valueOf(123));
		transacao.setValor(BigDecimal.valueOf(100.00));
		
		transacaoDto = new TransacaoDto();
		transacaoDto.setDataTransacao(LocalDateTime.now());
		transacaoDto.setIdConta(Long.valueOf(123));
		transacaoDto.setIdTransacao(Long.valueOf(123));
		transacaoDto.setValor(BigDecimal.valueOf(100.00));
		
		transacoes = new ArrayList<>();
		transacoes.add(transacao);
	}
	
	@Test
	public void toTransacaoTest() {
		Transacao transacao = transacaoParse.toTransacao(transacaoDto);
		
		Assertions.assertEquals(transacaoDto.getIdConta(), transacao.getIdConta());
		Assertions.assertEquals(transacaoDto.getIdTransacao(), transacao.getIdTransacao());
		Assertions.assertEquals(transacaoDto.getDataTransacao(), transacao.getDataTransacao());
		Assertions.assertEquals(transacaoDto.getValor(), transacao.getValor());
	}
	
	@Test
	public void toTransacaoNullTest() {
		transacaoDto = null;
		
		Transacao transacao = transacaoParse.toTransacao(transacaoDto);
		
		Assertions.assertNotNull(transacao);
	}
	
	@Test
	public void toTransacaoDtoTest() {
		TransacaoDto transacaoDto = transacaoParse.toTransacaoDto(transacao);
		
		Assertions.assertEquals(transacao.getIdConta(), transacaoDto.getIdConta());
		Assertions.assertEquals(transacao.getIdTransacao(), transacaoDto.getIdTransacao());
		Assertions.assertEquals(transacao.getDataTransacao(), transacaoDto.getDataTransacao());
		Assertions.assertEquals(transacao.getValor(), transacaoDto.getValor());
	}
	
	@Test
	public void toTransacaoDtoNullTest() {
		transacao = null;
		
		TransacaoDto transacaoDto = transacaoParse.toTransacaoDto(transacao);
		
		Assertions.assertNotNull(transacaoDto);
	}
	
	@Test
	public void toTransacoesDtoTest() {
		List<TransacaoDto> transacoesDto = transacaoParse.toTransacoesDto(transacoes);
		
		Assertions.assertNotNull(transacoesDto);
		Assertions.assertEquals(transacao.getIdConta(), transacoesDto.get(0).getIdConta());
		Assertions.assertEquals(transacao.getIdTransacao(), transacoesDto.get(0).getIdTransacao());
		Assertions.assertEquals(transacao.getDataTransacao(), transacoesDto.get(0).getDataTransacao());
		Assertions.assertEquals(transacao.getValor(), transacoesDto.get(0).getValor());
	}
	
	@Test
	public void toTransacoesDtoNullTest() {
		transacoes = null;
		
		List<TransacaoDto> transacoesDto = transacaoParse.toTransacoesDto(transacoes);
		
		Assertions.assertNotNull(transacoesDto);
	}
	
}
