package br.com.brienze.desafio.dock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_conta")
public class ContaEntity {

	@Id
	@Column(name = "id_conta")
	private Long id;
	
}
