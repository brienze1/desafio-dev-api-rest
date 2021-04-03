package br.com.brienze.desafio.dock.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;
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
	
	private Transacao transacao;
	private TransacaoEntity transacaoEntity;
	private TransacaoEntity transacaoEntityCadastrada;
	private Transacao transacaoCadastrada;
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();

		transacaoEntity = new TransacaoEntity();
		
		transacaoEntityCadastrada = new TransacaoEntity();
		
		transacaoCadastrada = new Transacao();
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
	
}
