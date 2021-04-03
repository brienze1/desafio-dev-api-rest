package br.com.brienze.desafio.dock.rules;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.exception.ValidationException;
import br.com.brienze.desafio.dock.service.ContaService;

@ExtendWith(SpringExtension.class)
public class TransacaoRulesTest {

	@InjectMocks
	private TransacaoRules transacaoRules;
	
	@Mock
	private ContaService contaService;
	
	@Mock
	private DateRules dateRules;
	
	private Transacao transacao;
	private Conta conta;
	private String dataInicioString;
	private String dataFimString;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Integer quantidade;
	private Integer pagina;
	private Long idConta;
	
	@BeforeEach
	public void init() {
		transacao = new Transacao();
		transacao.setIdConta(Long.valueOf(123));
		transacao.setValor(BigDecimal.valueOf(300.00));
		
		conta = new Conta();
		conta.setSaldo(BigDecimal.valueOf(300.00));
		conta.setFlagAtivo(true);
		
		dataInicioString = "25/01/1995";
		dataFimString = "25/01/1996";
		
		dataInicio = LocalDateTime.MAX;
		dataFim = LocalDateTime.MIN;
		
		quantidade = 1;
		pagina = 1;
		idConta = Long.valueOf(123);
	}
	
	@Test
	public void validateDepositoSuccessTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		boolean valid = transacaoRules.validateDeposito(transacao);
		
		Mockito.verify(contaService).consulta(transacao.getIdConta());
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validateDepositoErrorTransacaoNullTest() {
		transacao = null;

		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "transacao nao pode ser nula");
	}
	
	@Test
	public void validateDepositoErrorIdContaNullTest() {
		transacao.setIdConta(null);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "id_conta nao pode ser nulo");
	}
	
	@Test
	public void validateDepositoErrorValorNullTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(null);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "valor nao pode ser nulo");
	}
	
	@Test
	public void validateDepositoErrorValorMenorQueZeroTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(BigDecimal.valueOf(-100.00));
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "valor nao pode ser menor ou igual a zero");
	}
	
	@Test
	public void validateDepositoErrorValorZeroTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(BigDecimal.ZERO);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "valor nao pode ser menor ou igual a zero");
	}
	
	@Test
	public void validateDepositoErrorContaBloqueadaTest() {
		conta.setFlagAtivo(false);
		
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateDeposito(transacao), "transacao recusada, conta bloqueada");
	}
	
	@Test
	public void validateSaqueSuccesTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		boolean valid = transacaoRules.validateSaque(transacao);

		Mockito.verify(contaService, Mockito.times(2)).consulta(transacao.getIdConta());
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validateSaqueErrorSaldoInsuficienteTest() {
		transacao.setValor(BigDecimal.valueOf(1000.00));
		
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "saldo insuficiente");
		
		Mockito.verify(contaService, Mockito.times(2)).consulta(transacao.getIdConta());
	}
	
	@Test
	public void validateSaqueErrorTransacaoNullTest() {
		transacao = null;

		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "transacao nao pode ser nula");
	}
	
	@Test
	public void validateSaqueErrorIdContaNullTest() {
		transacao.setIdConta(null);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "id_conta nao pode ser nulo");
	}
	
	@Test
	public void validateSaqueErrorValorNullTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(null);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "valor nao pode ser nulo");
		
		Mockito.verify(contaService).consulta(transacao.getIdConta());
	}
	
	@Test
	public void validateSaqueErrorValorMenorQueZeroTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(BigDecimal.valueOf(-100.00));
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "valor nao pode ser menor ou igual a zero");
		
		Mockito.verify(contaService).consulta(transacao.getIdConta());
	}
	
	@Test
	public void validateSaqueErrorValorZeroTest() {
		Mockito.when(contaService.consulta(transacao.getIdConta())).thenReturn(conta);
		
		transacao.setValor(BigDecimal.ZERO);
		
		Assertions.assertThrows(ValidationException.class, () -> transacaoRules.validateSaque(transacao), "valor nao pode ser menor ou igual a zero");
		
		Mockito.verify(contaService).consulta(transacao.getIdConta());
	}
	
	@Test
	public void validatevalidateParametrosExtratoSuccessTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		
		boolean valid = transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, quantidade, pagina);
		
		Mockito.verify(contaService).consulta(idConta);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validatevalidateParametrosExtratoSuccessDataFimNulaTest() {
		boolean valid = transacaoRules.validateParametrosExtrato(idConta, null, dataInicioString, quantidade, pagina);
		
		Mockito.verify(contaService).consulta(idConta);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validatevalidateParametrosExtratoSuccessDataInicioNulaTest() {
		boolean valid = transacaoRules.validateParametrosExtrato(idConta, dataFimString, null, quantidade, pagina);
		
		Mockito.verify(contaService).consulta(idConta);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validatevalidateParametrosExtratoErroContaNullTest() {
		Assertions.assertThrows(
				ValidationException.class, 
				() -> transacaoRules.validateParametrosExtrato(null, dataFimString, dataInicioString, quantidade, pagina), 
				"id_conta nao pode ser nulo");
	}
	
	@Test
	public void validatevalidateParametrosExtratoErroDataFimAnteriorADataInicioTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataFim);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataInicio);
		
		Assertions.assertThrows(
				ValidationException.class, 
				() -> transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, quantidade, pagina), 
				"data_inicio deve vir antes da data_fim");
	}
	
	@Test
	public void validatevalidateParametrosExtratoErroPaginaNegativaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		
		pagina = -1;
		
		Assertions.assertThrows(
				ValidationException.class, 
				() -> transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, quantidade, pagina), 
				"pagina deve ser maior ou igual a zero");
	}
	
	@Test
	public void validatevalidateParametrosExtratoSuccessPaginaNulaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		
		boolean valid = transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, quantidade, null);
		
		Mockito.verify(contaService).consulta(idConta);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validatevalidateParametrosExtratoErroQuantidadeNegativaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		
		quantidade = -1;
		
		Assertions.assertThrows(
				ValidationException.class, 
				() -> transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, quantidade, pagina), 
				"quantidade deve ser maior ou igual a zero");
	}
	
	@Test
	public void validatevalidateParametrosExtratoSuccessQuantidadeNulaTest() {
		Mockito.when(dateRules.validate(dataInicioString)).thenReturn(dataInicio);
		Mockito.when(dateRules.validate(dataFimString)).thenReturn(dataFim);
		
		boolean valid = transacaoRules.validateParametrosExtrato(idConta, dataFimString, dataInicioString, null, pagina);
		
		Mockito.verify(contaService).consulta(idConta);
		
		Assertions.assertTrue(valid);
	}
	
}
