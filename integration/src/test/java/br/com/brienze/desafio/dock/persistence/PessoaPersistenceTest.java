package br.com.brienze.desafio.dock.persistence;

import java.util.Optional;

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
	private PessoaEntityParse pessoaParse;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	private Pessoa pessoa;
	private PessoaEntity pessoaEntity;
	private PessoaEntity pessoaEntitySaved;
	private Pessoa pessoaSaved;
	private Long idPessoa;
	
	@BeforeEach
	public void init() {
		pessoa = new Pessoa();
		
		pessoaEntity = new PessoaEntity();

		pessoaEntitySaved = new PessoaEntity();

		pessoaSaved = new Pessoa();
		
		idPessoa = Long.valueOf(123);
	}
	
	@Test
	public void saveTest() {
		Mockito.when(pessoaParse.toPessoaEntity(pessoa)).thenReturn(pessoaEntity);
		Mockito.when(pessoaRepository.save(pessoaEntity)).thenReturn(pessoaEntitySaved);
		Mockito.when(pessoaParse.toPessoa(pessoaEntitySaved)).thenReturn(pessoaSaved);
		
		Pessoa pessoaResponse = pessoaPersistence.save(pessoa);
		
		Mockito.verify(pessoaParse).toPessoaEntity(pessoa);
		Mockito.verify(pessoaRepository).save(pessoaEntity);
		Mockito.verify(pessoaParse).toPessoa(pessoaEntitySaved);
		
		Assertions.assertNotNull(pessoaResponse);
	}
	
	@Test
	public void buscaTest() {
		Mockito.when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaEntity));
		Mockito.when(pessoaParse.toPessoa(Optional.of(pessoaEntity))).thenReturn(Optional.of(pessoaSaved));
		
		Optional<Pessoa> pessoaResponse = pessoaPersistence.busca(idPessoa);
		
		Mockito.verify(pessoaRepository).findById(idPessoa);
		Mockito.verify(pessoaParse).toPessoa(Optional.of(pessoaEntity));
		
		Assertions.assertNotNull(pessoaResponse);
		Assertions.assertTrue(pessoaResponse.isPresent());
	}
	
}
