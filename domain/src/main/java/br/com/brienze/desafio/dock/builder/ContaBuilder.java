package br.com.brienze.desafio.dock.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Conta;

@Component
public class ContaBuilder {

	public Conta buildNovaConta(Conta conta) {
		Conta contaNova = new Conta();
		
		contaNova.setIdPessoa(conta.getIdPessoa());
		contaNova.setLimiteSaqueDiario(conta.getLimiteSaqueDiario());
		contaNova.setTipoConta(conta.getTipoConta());
		contaNova.setDataCriacao(LocalDateTime.now());
		contaNova.setFlagAtivo(true);
		contaNova.setSaldo(BigDecimal.ZERO);
		
		return contaNova;
	}

}
