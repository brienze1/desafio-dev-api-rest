package br.com.brienze.desafio.dock.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_transacao")
public class TransacaoEntity {

	@Id
	@Column(name = "id_transacao")
	private Long id;
	
	@Column(name = "data_transcao")
	private LocalDateTime dataTransacao;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(LocalDateTime dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	
}
