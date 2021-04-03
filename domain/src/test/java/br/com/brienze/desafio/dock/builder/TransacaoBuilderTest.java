package br.com.brienze.desafio.dock.builder;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Transacao;

@ExtendWith(SpringExtension.class)
public class TransacaoBuilderTest {

	@InjectMocks
	private TransacaoBuilder transacaoBuilder;
	
	@Test
	public void buidTest() {
		Transacao transacao = new Transacao();
		transacao.setIdConta(Long.valueOf(123));
		transacao.setValor(BigDecimal.valueOf(100.00));
		
		Transacao transacaoRealizada = transacaoBuilder.buildTransacao(transacao);
		
		Assertions.assertEquals(transacao.getIdConta(), transacaoRealizada.getIdConta());
		Assertions.assertEquals(transacao.getValor(), transacaoRealizada.getValor());
		Assertions.assertNotNull(transacaoRealizada.getDataTransacao());
		Assertions.assertNull(transacaoRealizada.getIdTransacao());
	}
	
}
