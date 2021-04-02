package br.com.brienze.desafio.dock.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tb_pessoa")
public class PessoaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_pessoa")
	private Long idPessoa;
	
	@Column(name = "nome", columnDefinition = "VARCHAR(255)", nullable = false)
	private String nome;
	
	@Column(name = "cpf", columnDefinition = "VARCHAR(11)", nullable = false)
	private String cpf;
	
	@Column(name = "data_nascimento", columnDefinition = "DATE", nullable = false)
	private LocalDate dataNascimento;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="idPessoa", cascade={CascadeType.ALL})
	private Set<ContaEntity> contas;

	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long id) {
		this.idPessoa = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
