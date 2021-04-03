package br.com.brienze.desafio.dock.rules;

import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.exception.NotFoundException;
import br.com.brienze.desafio.dock.exception.ValidationException;
import br.com.brienze.desafio.dock.service.PessoaService;

@ExtendWith(SpringExtension.class)
public class PessoaRulesTest {
	
	@InjectMocks
	private PessoaRules pessoaRules;
	
	@Mock
	private DateRules dateRules;
	
	@Mock
	private PessoaService pessoaService;
	
	private Pessoa pessoa;
	private Long idPessoa;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		pessoa.setCpf("12345678901");
		pessoa.setDataNascimento("25/01/1995");
		pessoa.setNome(UUID.randomUUID().toString());
		
		idPessoa = Long.valueOf(122);
	}
	
	@Test
	public void validateTest() {
		boolean valid = pessoaRules.validate(pessoa);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validatePessoaNullTest() {
		pessoa = null;
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "objeto pessoa nao pode ser nulo");
	}
	
	@Test
	public void validateNomeNuloTest() {
		pessoa.setNome(null);
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "nome nao pode ser nulo ou em branco");
	}
	
	@Test
	public void validateNomeEmBrancoTest() {
		pessoa.setNome(" ");
		 
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "nome nao pode ser nulo ou em branco");
	}
	
	@Test
	public void validateCpfNuloTest() {
		pessoa.setCpf(null);
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "nome nao pode ser nulo ou em branco");
	}
	
	@Test
	public void validateCpfEmBrancoTest() {
		pessoa.setCpf(" ");
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "nome nao pode ser nulo ou em branco");
	}
	
	@Test
	public void validateDataNascimentoNuloTest() {
		pessoa.setDataNascimento(null);
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "data_nascimento nao pode ser nula ou em branco");
	}
	
	@Test
	public void validateDataNascimentoEmBrancoTest() {
		pessoa.setDataNascimento(" ");
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "data_nascimento nao pode ser nula ou em branco");
	}
	
	@Test
	public void validateCpfMenorQue11Test() {
		pessoa.setCpf("1234567890");
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "cpf deve conter 11 caracteres");
	}
	
	@Test
	public void validateCpfMaiorQue11Test() {
		pessoa.setCpf("123456789011");
		
		Assertions.assertThrows(ValidationException.class, () -> pessoaRules.validate(pessoa), "cpf deve conter 11 caracteres");
	}
	
	@Test
	public void validateDataNascimentoForaDoPadraoTest() {
		pessoa.setDataNascimento("25-01-1995");
		
		Mockito.when(dateRules.validate(pessoa.getDataNascimento())).thenThrow(new DateTimeParseException("data_nascimento deve ser informada no padrao dd/MM/yyyy", pessoa.getDataNascimento(), 1));
		
		Assertions.assertThrows(DateTimeParseException.class, () -> pessoaRules.validate(pessoa), "data_nascimento deve ser informada no padrao dd/MM/yyyy");
	}
	
	@Test
	public void validateIdPessoaSuccessTest() {
		Mockito.when(pessoaService.busca(idPessoa)).thenReturn(Optional.of(pessoa));
		
		boolean valid = pessoaRules.validate(idPessoa);
		
		Mockito.verify(pessoaService).busca(idPessoa);
		
		Assertions.assertTrue(valid);
	}
	
	@Test
	public void validateIdPessoaErrorTest() {
		Mockito.when(pessoaService.busca(idPessoa)).thenReturn(Optional.ofNullable(null));
		
		Assertions.assertThrows(NotFoundException.class, () -> pessoaRules.validate(idPessoa), "id_pessoa nao cadastrado no sistema");

		Mockito.verify(pessoaService).busca(idPessoa);
	}

}
