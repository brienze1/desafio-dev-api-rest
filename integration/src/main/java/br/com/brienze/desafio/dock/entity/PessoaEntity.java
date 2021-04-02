package br.com.brienze.desafio.dock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_pessoa")
public class PessoaEntity {

	@Id
	@Column(name = "id_pessoa")
	private Long id;
	
}
