package br.com.brienze.desafio.dock.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.exception.NotFoundException;
import br.com.brienze.desafio.dock.exception.ValidationException;

@ExtendWith(SpringExtension.class)
public class ContaRulesTest {

	@InjectMocks
	private ContaRules contaRules;
	
	@Mock
	private PessoaRules pessoaRules;
	
	private Conta conta;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		conta.setDataCriacao(LocalDateTime.now());
		conta.setFlagAtivo(true);
		conta.setIdConta(Long.valueOf(123));
		conta.setIdPessoa(Long.valueOf(123));
		conta.setLimiteSaqueDiario(BigDecimal.valueOf(100.00));
		conta.setSaldo(BigDecimal.valueOf(200.00));
		conta.setTipoConta(123);
	}
	
	@Test
	public void validateSuccessTest() {
		Mockito.when(pessoaRules.validate(conta.getIdPessoa())).thenReturn(true);
		
		boolean valid = contaRules.validate(conta);
		
		Mockito.verify(pessoaRules).validate(conta.getIdPessoa());
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validateErrorContaNulaTest() {
		conta = null;
		
		Assertions.assertThrows(ValidationException.class, () -> contaRules.validate(conta), "conta nao pode ser nula");
	}
	
	@Test
	public void validateErrorLimiteZeroTest() {
		conta.setLimiteSaqueDiario(BigDecimal.valueOf(0.0));
		
		Assertions.assertThrows(ValidationException.class, () -> contaRules.validate(conta), "limite_saque_diario nao pode ser menor ou igual a zero");
	}
	
	@Test
	public void validateErrorTipoContaMenorZeroTest() {
		conta.setTipoConta(-1);
		
		Assertions.assertThrows(ValidationException.class, () -> contaRules.validate(conta), "tipo_conta nao pode ser menor ou igual a zero");
	}
	
	@Test
	public void validateErrorPessoaNaoEncontradaTest() {
		Mockito.when(pessoaRules.validate(conta.getIdPessoa())).thenThrow(new NotFoundException("id_pessoa nao cadastrado no sistema"));
		
		Assertions.assertThrows(NotFoundException.class, () -> contaRules.validate(conta), "id_pessoa nao cadastrado no sistema");
	}
	
	@Test
	public void validateIdContaSuccessTest() {
		boolean valid = contaRules.validate(Optional.of(conta));
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validateIdContaErrorTest() {
		Assertions.assertThrows(NotFoundException.class, () -> contaRules.validate(Optional.ofNullable(null)), "id_conta nao cadastrado no sistema");
	}
	
}
