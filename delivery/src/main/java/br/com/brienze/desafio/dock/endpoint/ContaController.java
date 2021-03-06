package br.com.brienze.desafio.dock.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brienze.desafio.dock.dto.ContaDto;
import br.com.brienze.desafio.dock.entity.Conta;
import br.com.brienze.desafio.dock.parse.ContaParse;
import br.com.brienze.desafio.dock.service.ContaService;

@RestController
@RequestMapping("/v1/contas")
public class ContaController {

	@Autowired
	private ContaParse contaParse;
	
	@Autowired
	private ContaService contaService;
	
	@PostMapping
	public ResponseEntity<ContaDto> cadastro(@RequestBody ContaDto contaDto) {
		Conta conta = contaParse.toConta(contaDto);
		
		Conta contaCadastrada = contaService.cadastro(conta);
		
		ContaDto contaDtoCadastrada = contaParse.toContaDto(contaCadastrada);
		
		return new ResponseEntity<ContaDto>(contaDtoCadastrada, HttpStatus.CREATED);
	}

	@GetMapping("/{id_conta}")
	public ResponseEntity<ContaDto> consultaSaldo(@PathVariable(name = "id_conta", required = true) Long idConta) {
		Conta contaCadastrada = contaService.consulta(idConta);
		
		ContaDto contaDtoCadastrada = contaParse.toContaDto(contaCadastrada);
		
		return new ResponseEntity<ContaDto>(contaDtoCadastrada, HttpStatus.OK);
	}
	
	@PatchMapping("/bloqueios/{id_conta}")
	public ResponseEntity<ContaDto> bloqueiaConta(@PathVariable(name = "id_conta", required = true) Long idConta) {
		Conta contaCadastrada = contaService.bloqueiaConta(idConta);
		
		ContaDto contaDtoCadastrada = contaParse.toContaDto(contaCadastrada);
		
		return new ResponseEntity<ContaDto>(contaDtoCadastrada, HttpStatus.OK);
	}
	
}
