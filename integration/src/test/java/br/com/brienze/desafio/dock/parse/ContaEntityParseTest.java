package br.com.brienze.desafio.dock.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.PessoaEntity;

@ExtendWith(SpringExtension.class)
public class ContaEntityParseTest {

	@InjectMocks
	private ContaEntityParse contaParse;
	
	private Conta conta;
	private ContaEntity contaEntity;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		conta.setDataCriacao(LocalDateTime.now());
		conta.setFlagAtivo(true);
		conta.setIdConta(Long.valueOf(1234));
		conta.setIdPessoa(Long.valueOf(12345));
		conta.setLimiteSaqueDiario(BigDecimal.valueOf(100.00));
		conta.setSaldo(BigDecimal.valueOf(200.00));
		conta.setTipoConta(123);

		contaEntity = new ContaEntity();
		contaEntity.setDataCriacao(LocalDateTime.now());
		contaEntity.setFlagAtivo(true);
		contaEntity.setIdConta(Long.valueOf(1234));
		contaEntity.setPessoa(new PessoaEntity());
		contaEntity.getPessoa().setIdPessoa(Long.valueOf(1234));
		contaEntity.setLimiteSaqueDiario(BigDecimal.valueOf(100.00));
		contaEntity.setSaldo(BigDecimal.valueOf(200.00));
		contaEntity.setTipoConta(123);
	}
	
	@Test
	public void toContaTest() {
		Conta conta = contaParse.toConta(contaEntity);
		
		Assertions.assertEquals(contaEntity.getIdConta(), conta.getIdConta());
		Assertions.assertEquals(contaEntity.getPessoa().getIdPessoa(), conta.getIdPessoa());
		Assertions.assertEquals(contaEntity.getDataCriacao(), conta.getDataCriacao());
		Assertions.assertEquals(contaEntity.getFlagAtivo(), conta.getFlagAtivo());
		Assertions.assertEquals(contaEntity.getLimiteSaqueDiario(), conta.getLimiteSaqueDiario());
		Assertions.assertEquals(contaEntity.getSaldo(), conta.getSaldo());
		Assertions.assertEquals(contaEntity.getTipoConta(), conta.getTipoConta());
	}
	
	@Test
	public void toContaNullTest() {
		contaEntity = null;
		
		Conta conta = contaParse.toConta(contaEntity);
		
		Assertions.assertNotNull(conta);
	}
	
	@Test
	public void toContaOtpionalTest() {
		Optional<Conta> conta = contaParse.toConta(Optional.of(contaEntity));
		
		Assertions.assertEquals(contaEntity.getIdConta(), conta.get().getIdConta());
		Assertions.assertEquals(contaEntity.getPessoa().getIdPessoa(), conta.get().getIdPessoa());
		Assertions.assertEquals(contaEntity.getDataCriacao(), conta.get().getDataCriacao());
		Assertions.assertEquals(contaEntity.getFlagAtivo(), conta.get().getFlagAtivo());
		Assertions.assertEquals(contaEntity.getLimiteSaqueDiario(), conta.get().getLimiteSaqueDiario());
		Assertions.assertEquals(contaEntity.getSaldo(), conta.get().getSaldo());
		Assertions.assertEquals(contaEntity.getTipoConta(), conta.get().getTipoConta());
	}
	
	@Test
	public void toContaOtpionalNullTest() {
		Optional<Conta> conta = contaParse.toConta(Optional.ofNullable(null));
		
		Assertions.assertNotNull(conta);
		Assertions.assertTrue(conta.isEmpty());
	}
	
	@Test
	public void toContaEntityTest() {
		ContaEntity contaEntity = contaParse.toContaEntity(conta);
		
		Assertions.assertEquals(conta.getIdConta(), contaEntity.getIdConta());
		Assertions.assertEquals(conta.getIdPessoa(), contaEntity.getPessoa().getIdPessoa());
		Assertions.assertEquals(conta.getDataCriacao(), contaEntity.getDataCriacao());
		Assertions.assertEquals(conta.getFlagAtivo(), contaEntity.getFlagAtivo());
		Assertions.assertEquals(conta.getLimiteSaqueDiario(), contaEntity.getLimiteSaqueDiario());
		Assertions.assertEquals(conta.getSaldo(), contaEntity.getSaldo());
		Assertions.assertEquals(conta.getTipoConta(), contaEntity.getTipoConta());
	}
	
	@Test
	public void toContaEntityNullTest() {
		ContaEntity contaEntity = contaParse.toContaEntity(null);
		
		Assertions.assertNotNull(contaEntity);
	}
	
}
