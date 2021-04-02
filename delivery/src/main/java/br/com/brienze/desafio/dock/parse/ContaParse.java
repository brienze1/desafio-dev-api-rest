package br.com.brienze.desafio.dock.parse;

import org.springframework.stereotype.Component;

import br.com.brienze.desafio.dock.dto.ContaDto;
import br.com.brienze.desafio.dock.entity.Conta;

@Component
public class ContaParse {

	public Conta toConta(ContaDto contaDto) {
		Conta conta = new Conta();
		
		if(contaDto != null) {
			conta.setDataCriacao(contaDto.getDataCriacao());
			conta.setFlagAtivo(contaDto.getFlagAtivo());
			conta.setIdConta(contaDto.getIdConta());
			conta.setIdPessoa(contaDto.getIdPessoa());
			conta.setLimiteSaqueDiario(contaDto.getLimiteSaqueDiario());
			conta.setSaldo(contaDto.getSaldo());
			conta.setTipoConta(contaDto.getTipoConta());
		}
		
		return conta;
	}

	public ContaDto toContaDto(Conta conta) {
		ContaDto contaDto = new ContaDto();
		
		if(conta != null) {
			contaDto.setDataCriacao(conta.getDataCriacao());
			contaDto.setFlagAtivo(conta.getFlagAtivo());
			contaDto.setIdConta(conta.getIdConta());
			contaDto.setIdPessoa(conta.getIdPessoa());
			contaDto.setLimiteSaqueDiario(conta.getLimiteSaqueDiario());
			contaDto.setSaldo(conta.getSaldo());
			contaDto.setTipoConta(conta.getTipoConta());
		}
		
		return contaDto;
	}

}
