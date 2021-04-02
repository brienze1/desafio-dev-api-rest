package br.com.brienze.desafio.dock.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parent;

@Entity
@Table(name="tb_conta")
public class ContaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id_conta")
	private Long idConta;

	@Parent
	private PessoaEntity idPessoa;
	
	@Column(name = "saldo", columnDefinition = "MONEY", nullable = false)
	private BigDecimal saldo;

	@Column(name = "limite_saque_diario", columnDefinition = "MONEY", nullable = false)
	private BigDecimal limiteSaqueDiario;

	@Column(name = "flag_ativo", columnDefinition = "BOOLEAN", nullable = false)
	private Boolean flagAtivo;
	
	@Column(name = "tipo_conta", columnDefinition = "INTEGER", nullable = false)
	private Integer tipoConta;

	@Column(name = "data_criacao", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime dataCriacao;

	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public PessoaEntity getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(PessoaEntity idPessoa) {
		this.idPessoa = idPessoa;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public BigDecimal getLimiteSaqueDiario() {
		return limiteSaqueDiario;
	}
	public void setLimiteSaqueDiario(BigDecimal limiteSaqueDiario) {
		this.limiteSaqueDiario = limiteSaqueDiario;
	}
	public Boolean getFlagAtivo() {
		return flagAtivo;
	}
	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	public Integer getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
}
