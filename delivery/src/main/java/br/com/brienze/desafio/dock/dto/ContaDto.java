package br.com.brienze.desafio.dock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ContaDto {

	@ApiModelProperty(hidden = true)
	@JsonProperty("id_conta")
	private Long idConta;
	
	@JsonProperty("id_pessoa")
	private Long idPessoa;

	@ApiModelProperty(hidden = true)
	@JsonProperty("saldo")
	private BigDecimal saldo;
	
	@JsonProperty("limite_saque_diario")
	private BigDecimal limiteSaqueDiario;

	@ApiModelProperty(hidden = true)
	@JsonProperty("flag_ativo")
	private Boolean flagAtivo;
	
	@JsonProperty("tipo_conta")
	private Integer tipoConta;

	@ApiModelProperty(hidden = true)
	@JsonProperty("data_criacao")
	private LocalDateTime dataCriacao;
	
	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
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
