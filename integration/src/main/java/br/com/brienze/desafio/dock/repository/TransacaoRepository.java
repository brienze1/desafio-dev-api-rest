package br.com.brienze.desafio.dock.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.TransacaoEntity;

public interface TransacaoRepository extends CrudRepository<TransacaoEntity, Long>{

	List<TransacaoEntity> findByContaAndDataTransacaoGreaterThanEqualAndDataTransacaoLessThanEqualOrderByDataTransacaoDesc(
			ContaEntity contaEntity, LocalDateTime dataInicio, LocalDateTime dataFim, Pageable page);

}
