package br.com.brienze.desafio.dock.parse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.dto.ContaDto;
import br.com.brienze.desafio.dock.entity.Conta;

@ExtendWith(SpringExtension.class)
public class ContaParseTest {

	@InjectMocks
	private ContaParse contaParse;

	private Conta conta;
	private ContaDto contaDto;

	@BeforeEach
	public void init() {
		conta = new Conta();
		conta.setDataCriacao(LocalDateTime.now());
		conta.setFlagAtivo(true);
		conta.setIdConta(Long.valueOf(123));
		conta.setIdPessoa(Long.valueOf(123));
		conta.setLimiteSaqueDiario(BigDecimal.valueOf(100.00));
		conta.setSaldo(BigDecimal.valueOf(200.00));
		conta.setTipoConta(11);

		contaDto = new ContaDto();
		contaDto.setDataCriacao(LocalDateTime.now());
		contaDto.setFlagAtivo(true);
		contaDto.setIdConta(Long.valueOf(123));
		contaDto.setIdPessoa(Long.valueOf(123));
		contaDto.setLimiteSaqueDiario(BigDecimal.valueOf(100.00));
		contaDto.setSaldo(BigDecimal.valueOf(200.00));
		contaDto.setTipoConta(11);
	}

	@Test
	public void toContaTest() {
		Conta conta = contaParse.toConta(contaDto);

		Assertions.assertEquals(contaDto.getIdConta(), conta.getIdConta());
		Assertions.assertEquals(contaDto.getIdPessoa(), conta.getIdPessoa());
		Assertions.assertEquals(contaDto.getDataCriacao(), conta.getDataCriacao());
		Assertions.assertEquals(contaDto.getFlagAtivo(), conta.getFlagAtivo());
		Assertions.assertEquals(contaDto.getLimiteSaqueDiario(), conta.getLimiteSaqueDiario());
		Assertions.assertEquals(contaDto.getSaldo(), conta.getSaldo());
		Assertions.assertEquals(contaDto.getTipoConta(), conta.getTipoConta());
	}

	@Test
	public void toContaNullTest() {
		Conta conta = contaParse.toConta(null);

		Assertions.assertNotNull(conta);
	}

	@Test
	public void toContaDtoTest() {
		ContaDto contaDto = contaParse.toContaDto(conta);

		Assertions.assertEquals(conta.getIdConta(), contaDto.getIdConta());
		Assertions.assertEquals(conta.getIdPessoa(), contaDto.getIdPessoa());
		Assertions.assertEquals(conta.getDataCriacao(), contaDto.getDataCriacao());
		Assertions.assertEquals(conta.getFlagAtivo(), contaDto.getFlagAtivo());
		Assertions.assertEquals(conta.getLimiteSaqueDiario(), contaDto.getLimiteSaqueDiario());
		Assertions.assertEquals(conta.getSaldo(), contaDto.getSaldo());
		Assertions.assertEquals(conta.getTipoConta(), contaDto.getTipoConta());
	}

	@Test
	public void toContaDtoNullTest() {
		ContaDto contaDto = contaParse.toContaDto(null);

		Assertions.assertNotNull(contaDto);
	}

}
