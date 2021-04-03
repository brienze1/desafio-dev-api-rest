package br.com.brienze.desafio.dock.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/extratos/{id_conta}")
	public ResponseEntity<List<TransacaoDto>> consultaExtrato(
			@PathVariable(name = "id_conta", required = true) Long idConta,
			@RequestParam(name = "data_inicio", required = false) String dataInicio,
			@RequestParam(name = "data_fim", required = false) String dataFim,
			@RequestParam(name = "quantidade", required = false) Integer quantidade,
			@RequestParam(name = "pagina", required = false) Integer pagina) {
		List<Transacao> transacoesCadastradas = transacaoService.consulta(idConta, dataInicio, dataFim, quantidade, pagina);
		
		List<TransacaoDto> transacoesDtoCadastradas = transacaoParse.toTransacoesDto(transacoesCadastradas);
		
		return new ResponseEntity<List<TransacaoDto>>(transacoesDtoCadastradas, HttpStatus.OK);
	}
	
}
