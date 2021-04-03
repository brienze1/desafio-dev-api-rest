package br.com.brienze.desafio.dock.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.parse.ContaEntityParse;
import br.com.brienze.desafio.dock.repository.ContaRepository;

@ExtendWith(SpringExtension.class)
public class ContaPersistenceTest {

	@InjectMocks
	private ContaPersistence contaPersistence;
	
	@Mock
	private ContaRepository contaRepository;

	@Mock
	private ContaEntityParse contaParse;
	
	private Conta conta;
	private ContaEntity contaEntity;
	private ContaEntity contaEntitySalva;
	private Conta contaSalva;
	
	@BeforeEach
	public void init() {
		conta = new Conta();
		conta.setIdPessoa(Long.valueOf(123));
		
		contaEntity = new ContaEntity();
		
		contaEntitySalva = new ContaEntity();
		
		contaSalva = new Conta();
	}
	
	@Test
	public void saveTest() {
		Mockito.when(contaParse.toContaEntity(conta)).thenReturn(contaEntity);
		Mockito.when(contaRepository.save(contaEntity)).thenReturn(contaEntitySalva);
		Mockito.when(contaParse.toConta(contaEntitySalva)).thenReturn(contaSalva);
		
		Conta contaResponse = contaPersistence.save(conta);
		
		Mockito.verify(contaParse).toContaEntity(conta);
		Mockito.verify(contaRepository).save(contaEntity);
		Mockito.verify(contaParse).toConta(contaEntitySalva);
		
		Assertions.assertNotNull(contaResponse);
	}
	
}
