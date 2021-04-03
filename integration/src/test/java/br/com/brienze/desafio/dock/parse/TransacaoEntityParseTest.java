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

import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;

@ExtendWith(SpringExtension.class)
public class TransacaoEntityParseTest {

	@InjectMocks
	private TransacaoEntityParse transacaoParse;
	
	private Transacao transacao;
	private TransacaoEntity transacaoEntity;
	private List<TransacaoEntity> transacoesEntity;
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacao.setDataTransacao(LocalDateTime.now());
		transacao.setIdConta(Long.valueOf(123));
		transacao.setIdTransacao(Long.valueOf(123));
		transacao.setValor(BigDecimal.valueOf(100.00));
		
		transacaoEntity = new TransacaoEntity();
		transacaoEntity.setDataTransacao(LocalDateTime.now());
		transacaoEntity.setConta(new ContaEntity());
		transacaoEntity.getConta().setIdConta(Long.valueOf(123));
		transacaoEntity.setIdTransacao(Long.valueOf(123));
		transacaoEntity.setValor(BigDecimal.valueOf(100.00));
		
		transacoesEntity = new ArrayList<>();
		transacoesEntity.add(transacaoEntity);
	}
	
	@Test
	public void toTransacaoTest() {
		Transacao transacao = transacaoParse.toTransacao(transacaoEntity);

		Assertions.assertEquals(transacaoEntity.getConta().getIdConta(), transacao.getIdConta());
		Assertions.assertEquals(transacaoEntity.getIdTransacao(), transacao.getIdTransacao());
		Assertions.assertEquals(transacaoEntity.getDataTransacao(), transacao.getDataTransacao());
		Assertions.assertEquals(transacaoEntity.getValor(), transacao.getValor());
	}
	
	@Test
	public void toTransacaoNullTest() {
		transacaoEntity = null;
		
		Transacao transacao = transacaoParse.toTransacao(transacaoEntity);
		
		Assertions.assertNotNull(transacao);
	}
	
	@Test
	public void toTransacaoEntityTest() {
		TransacaoEntity transacaoEntity = transacaoParse.toTransacaoEntity(transacao);

		Assertions.assertEquals(transacao.getIdConta(), transacaoEntity.getConta().getIdConta());
		Assertions.assertEquals(transacao.getIdTransacao(), transacaoEntity.getIdTransacao());
		Assertions.assertEquals(transacao.getDataTransacao(), transacaoEntity.getDataTransacao());
		Assertions.assertEquals(transacao.getValor(), transacaoEntity.getValor());
	}
	
	@Test
	public void toTransacaoEntityNullTest() {
		transacao = null;
		
		TransacaoEntity transacaoEntity = transacaoParse.toTransacaoEntity(transacao);

		Assertions.assertNotNull(transacaoEntity);
	}
	
	@Test
	public void toTransacoesTest() {
		List<Transacao> transacoes = transacaoParse.toTransacoes(transacoesEntity);
		
		Assertions.assertNotNull(transacoes);
		Assertions.assertFalse(transacoes.isEmpty());
		Assertions.assertEquals(transacaoEntity.getConta().getIdConta(), transacoes.get(0).getIdConta());
		Assertions.assertEquals(transacaoEntity.getIdTransacao(), transacoes.get(0).getIdTransacao());
		Assertions.assertEquals(transacaoEntity.getDataTransacao(), transacoes.get(0).getDataTransacao());
		Assertions.assertEquals(transacaoEntity.getValor(), transacoes.get(0).getValor());
	}
	
	@Test
	public void toTransacoesNullTest() {
		transacoesEntity = null;
		
		List<Transacao> transacoes = transacaoParse.toTransacoes(transacoesEntity);
		
		Assertions.assertNotNull(transacoes);
		Assertions.assertTrue(transacoes.isEmpty());
	}
	
}
