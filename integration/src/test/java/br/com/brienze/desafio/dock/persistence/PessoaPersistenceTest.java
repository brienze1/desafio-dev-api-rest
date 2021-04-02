package br.com.brienze.desafio.dock.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.entity.PessoaEntity;
import br.com.brienze.desafio.dock.parse.PessoaEntityParse;
import br.com.brienze.desafio.dock.repository.PessoaRepository;

@ExtendWith(SpringExtension.class)
public class PessoaPersistenceTest {

	@InjectMocks
	private PessoaPersistence pessoaPersistence;
	
	@Mock
	private PessoaEntityParse pessoaEntityParse;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	private Pessoa pessoa;
	private PessoaEntity pessoaEntity;
	private PessoaEntity pessoaEntitySaved;
	private Pessoa pessoaSaved;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		
		pessoaEntity = new PessoaEntity();

		pessoaEntitySaved = new PessoaEntity();

		pessoaSaved = new Pessoa();
	}
	
	@Test
	public void saveTest() {
		Mockito.when(pessoaEntityParse.toPessoaEntity(pessoa)).thenReturn(pessoaEntity);
		Mockito.when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntitySaved);
		Mockito.when(pessoaEntityParse.toPessoa(pessoaEntitySaved)).thenReturn(pessoaSaved);
		
		Pessoa pessoaResponse = pessoaPersistence.save(pessoa);
		
		Mockito.verify(pessoaEntityParse).toPessoaEntity(pessoa);
		Mockito.verify(pessoaRepository).save(pessoaEntity);
		Mockito.verify(pessoaEntityParse).toPessoa(pessoaEntitySaved);
		
		Assertions.assertNotNull(pessoaResponse);
	}
	
}
