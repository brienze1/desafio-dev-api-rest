package br.com.brienze.desafio.dock.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brienze.desafio.dock.dto.PessoaDto;
import br.com.brienze.desafio.dock.entity.Pessoa;
import br.com.brienze.desafio.dock.parse.PessoaParse;
import br.com.brienze.desafio.dock.service.PessoaService;

@RestController
@RequestMapping("/v1/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaParse pessoaParse;

	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping
	public ResponseEntity<PessoaDto> cadastro(@RequestBody PessoaDto pessoaDto) {
		Pessoa pessoa = pessoaParse.toPessoa(pessoaDto);
		
		Pessoa pessoaCadastrada = pessoaService.cadastro(pessoa);
		
		PessoaDto pessoaDtoCadastrada = pessoaParse.toPessoaDto(pessoaCadastrada);
		
		return new ResponseEntity<PessoaDto>(pessoaDtoCadastrada, HttpStatus.CREATED);
	}

}
