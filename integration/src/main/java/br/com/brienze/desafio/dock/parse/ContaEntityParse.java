package br.com.brienze.desafio.dock.parse;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.entity.ContaEntity;
import br.com.brienze.desafio.dock.entity.PessoaEntity;

@Component
public class ContaEntityParse {

	public ContaEntity toContaEntity(Conta conta) {
		ContaEntity contaEntity = new ContaEntity();
		
		if(conta != null) {
			contaEntity.setIdConta(conta.getIdConta());
			contaEntity.setDataCriacao(conta.getDataCriacao());
			contaEntity.setFlagAtivo(conta.getFlagAtivo());
			contaEntity.setLimiteSaqueDiario(conta.getLimiteSaqueDiario());
			contaEntity.setSaldo(conta.getSaldo());
			contaEntity.setTipoConta(conta.getTipoConta());
			contaEntity.setPessoa(new PessoaEntity());
			contaEntity.getPessoa().setIdPessoa(conta.getIdPessoa());
		}
		
		return contaEntity;
	}

	public Conta toConta(ContaEntity contaEntity) {
		Conta conta = new Conta();
		
		if(contaEntity != null) {
			conta.setDataCriacao(contaEntity.getDataCriacao());
			conta.setFlagAtivo(contaEntity.getFlagAtivo());
			conta.setLimiteSaqueDiario(contaEntity.getLimiteSaqueDiario());
			conta.setSaldo(contaEntity.getSaldo());
			conta.setTipoConta(contaEntity.getTipoConta());
			conta.setIdPessoa(contaEntity.getPessoa().getIdPessoa());
			conta.setIdConta(contaEntity.getIdConta());
		}
		
		return conta;
	}

}
