package br.com.brienze.desafio.dock.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brienze.desafio.dock.dto.TransacaoDto;
import br.com.brienze.desafio.dock.entity.Transacao;
import br.com.brienze.desafio.dock.parse.TransacaoParse;
import br.com.brienze.desafio.dock.service.TransacaoService;

@RestController
@RequestMapping("/v1/transacoes")
public class TransacoesController {
	
	@Autowired
	private TransacaoParse transacaoParse;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@PostMapping("/depositos")
	public ResponseEntity<TransacaoDto> deposito(@RequestBody TransacaoDto transacaoDto) {
		Transacao transacao = transacaoParse.toTransacao(transacaoDto);
		
		Transacao transacaoRealziada = transacaoService.deposito(transacao);
		
		TransacaoDto transacaoDtoRealizada = transacaoParse.toTransacaoDto(transacaoRealziada);
		
		return new ResponseEntity<TransacaoDto>(transacaoDtoRealizada, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/saques")
	public ResponseEntity<TransacaoDto> saque(@RequestBody TransacaoDto transacaoDto) {
		Transacao transacao = transacaoParse.toTransacao(transacaoDto);
		
		Transacao transacaoRealziada = transacaoService.saque(transacao);
		
		TransacaoDto transacaoDtoRealizada = transacaoParse.toTransacaoDto(transacaoRealziada);
		
		return new ResponseEntity<TransacaoDto>(transacaoDtoRealizada, HttpStatus.ACCEPTED);
	}
	
}
