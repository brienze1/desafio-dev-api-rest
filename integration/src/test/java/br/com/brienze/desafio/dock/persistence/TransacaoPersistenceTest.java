package br.com.brienze.desafio.dock.persistence;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;
import br.com.brienze.desafio.dock.parse.ContaEntityParse;
import br.com.brienze.desafio.dock.parse.TransacaoEntityParse;
import br.com.brienze.desafio.dock.repository.TransacaoRepository;

@ExtendWith(SpringExtension.class)
public class TransacaoPersistenceTest {

	@InjectMocks
	private TransacaoPersistence transacaoPersistence;
	
	@Mock
	private TransacaoEntityParse transacaoParse;
	
	@Mock
	private TransacaoRepository transacaoRepository;

	@Mock
	private ContaEntityParse contaParse;
	
	private Transacao transacao;
	private TransacaoEntity transacaoEntity;
	private TransacaoEntity transacaoEntityCadastrada;
	private Transacao transacaoCadastrada;
	private Conta conta;
	private ContaEntity contaEntity;
	private List<TransacaoEntity> transacoesEntity;
	private List<Transacao> transacoes;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Integer pagina;
	private Integer quantidade;
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacaoEntity = new TransacaoEntity();
		transacaoEntityCadastrada = new TransacaoEntity();
		transacaoCadastrada = new Transacao();
		conta = new Conta();
		contaEntity = new ContaEntity();
		dataInicio = LocalDateTime.now();
		dataFim = LocalDateTime.now();
		pagina = 1;
		quantidade = 10;
		
		transacoesEntity = new ArrayList<>();
		for(int i=0; i<10; i++) {
			transacoesEntity.add(new TransacaoEntity());
		}
		
		transacoes = new ArrayList<>();
		for(int i=0; i<10; i++) {
			transacoes.add(new Transacao());
		}
	}
	
	@Test
	public void saveTest() {
		Mockito.when(transacaoParse.toTransacaoEntity(transacao)).thenReturn(transacaoEntity);
		Mockito.when(transacaoRepository.save(transacaoEntity)).thenReturn(transacaoEntityCadastrada);
		Mockito.when(transacaoParse.toTransacao(transacaoEntityCadastrada)).thenReturn(transacaoCadastrada);
		
		Transacao transacaoCadastrada = transacaoPersistence.save(transacao);
		
		Mockito.verify(transacaoParse).toTransacaoEntity(transacao);
		Mockito.verify(transacaoRepository).save(transacaoEntity);
		Mockito.verify(transacaoParse).toTransacao(transacaoEntityCadastrada);
		
		Assertions.assertNotNull(transacaoCadastrada);
	}
	
	@Test
	public void buscaExtratoTest() {
		Mockito.when(contaParse.toContaEntity(conta)).thenReturn(contaEntity);
		Mockito.when(transacaoRepository.findByContaAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqualOrderByDataTransacaoDesc(
				contaEntity, 
				dataInicio, 
				dataFim, 
				PageRequest.of(pagina, quantidade))).thenReturn(transacoesEntity);
		Mockito.when(transacaoParse.toTransacoes(transacoesEntity)).thenReturn(transacoes);
		
		List<Transacao> transacoesCadastrada = transacaoPersistence.buscaExtrato(conta, dataInicio, dataFim, quantidade, pagina);
		
		Mockito.verify(contaParse).toContaEntity(conta);
		Mockito.verify(transacaoRepository).findByContaAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqualOrderByDataTransacaoDesc(
				contaEntity, 
				dataInicio, 
				dataFim, 
				PageRequest.of(pagina, quantidade));
		Mockito.verify(transacaoParse).toTransacoes(transacoesEntity);
		
		Assertions.assertNotNull(transacoesCadastrada);
		Assertions.assertFalse(transacoesCadastrada.isEmpty());
	}
	
}
