package br.com.brienze.desafio.dock.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_conta")
public class ContaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_conta")
	private Long idConta;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="id_pessoa")
	private PessoaEntity pessoa;
	
	@Column(name = "saldo")
	private BigDecimal saldo;

	@Column(name = "limite_saque_diario")
	private BigDecimal limiteSaqueDiario;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	@Column(name = "tipo_conta")
	private Integer tipoConta;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public PessoaEntity getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaEntity pessoa) {
		this.pessoa = pessoa;
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
