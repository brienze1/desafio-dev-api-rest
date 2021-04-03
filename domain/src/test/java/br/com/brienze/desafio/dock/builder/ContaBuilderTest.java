package br.com.brienze.desafio.dock.builder;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;

@ExtendWith(SpringExtension.class)
public class ContaBuilderTest {
	
	@InjectMocks
	private ContaBuilder contaBuilder;
	
	private Conta conta;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		conta.setLimiteSaqueDiario(BigDecimal.valueOf(100.0));
		conta.setTipoConta(1);
		conta.setIdPessoa(Long.valueOf(100));
	}
	
	@Test
	public void buildContaNovaTest() {
		Conta contaNova = contaBuilder.buildNovaConta(conta);

		Assertions.assertEquals(conta.getIdPessoa(), contaNova.getIdPessoa());
		Assertions.assertEquals(conta.getLimiteSaqueDiario(), contaNova.getLimiteSaqueDiario());
		Assertions.assertEquals(conta.getTipoConta(), contaNova.getTipoConta());
		Assertions.assertNotNull(contaNova.getDataCriacao());
		Assertions.assertNotNull(contaNova.getSaldo());
		Assertions.assertTrue(contaNova.getFlagAtivo());
	}

}
