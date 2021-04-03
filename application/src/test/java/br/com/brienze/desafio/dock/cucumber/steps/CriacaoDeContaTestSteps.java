package br.com.brienze.desafio.dock.cucumber.steps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brienze.desafio.dock.dto.ContaDto;
import br.com.brienze.desafio.dock.dto.PessoaDto;
import br.com.brienze.desafio.dock.repository.ContaRepository;
import br.com.brienze.desafio.dock.repository.PessoaRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class CriacaoDeContaTestSteps {
	
	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private ResponseEntity<String> responseString;
	private ResponseEntity<ContaDto> response;
	private TypeReference<Map<String, Object>> typeReference;
	private Map<String, String> dataMap;
	private ContaDto contaDto;
	private HttpStatusCodeException e;
	
	@PostConstruct
	public void init() {
		contaRepository.deleteAll();
		pessoaRepository.deleteAll();
		
		typeReference = new TypeReference<Map<String, Object>>() {};
		
		dataMap = new HashMap<>();
		
		contaDto = new ContaDto();
	}

	@Dado("que tenha sido cadastrada a seguinte pessoa anteriormente no sistema") 
	public void que_tenha_sido_cadastrada_a_seguinte_pessoa_anteriormente_no_sistema(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);
		
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setCpf(pessoaMap.get("cpf"));
		pessoaDto.setDataNascimento(pessoaMap.get("data_nascimento"));
		pessoaDto.setNome(pessoaMap.get("nome"));
		
		responseString = exchange("/v1/pessoas", pessoaDto, String.class);
	}

	@Dado("o {string} gerado tenha sido guardado")
	public void o_gerado_tenha_sido_guardado(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> pessoaMap = mapper.readValue(responseString.getBody(), typeReference);
		
		dataMap.put(campo, String.valueOf(pessoaMap.get(campo)));
	}
	
	@Dado("que nao exista nenhuma pessoa cadastrada para o idPessoa {int}") 
	public void que_nao_exista_nenhuma_pessoa_cadastrada_para_o_idPessoa(Integer idPessoa) {
		try {
			pessoaRepository.deleteById(Long.valueOf(idPessoa));
		} catch (Exception e) {
		}
	}
	
	@Dado("o {string} {int} tenha sido guardado")
	public void o_tenha_sido_guardado(String campo, Integer valor) {
		dataMap.put(campo, String.valueOf(valor));
	}

	@Quando("for solicitada a criacao da conta abaixo para o id_pessoa reservado") 
	public void for_solicitada_a_criacao_da_conta_abaixo_para_o_id_pessoa_reservado(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);
		
		if(contaMap.containsKey("limite_saque_diario")) {
			contaDto.setLimiteSaqueDiario(BigDecimal.valueOf(Double.valueOf(contaMap.get("limite_saque_diario"))));
		}
		contaDto.setTipoConta(Integer.valueOf(contaMap.get("tipo_conta")));
		contaDto.setIdPessoa(Long.valueOf(dataMap.get("id_pessoa")));
		
		try {
			response = exchange("/v1/contas", contaDto, ContaDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}
	
	@Entao("deve ser retornado o cadastro completo da conta com os campos") 
	public void deve_ser_retornado_o_cadastro_completo_da_conta_com_os_campos(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);
		
		if(contaMap.containsKey("limite_saque_diario")) {
			Assert.assertEquals(BigDecimal.valueOf(Double.valueOf(contaMap.get("limite_saque_diario"))), response.getBody().getLimiteSaqueDiario());
		}
		Assert.assertEquals(contaMap.get("saldo"), response.getBody().getSaldo().toString());
		Assert.assertEquals(Integer.valueOf(contaMap.get("tipo_conta")), response.getBody().getTipoConta());
	}

	@Entao("o campo {string} deve vir preenchido") 
	public void o_campo_deve_vir_preenchido(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
	}

	@Entao("o campo {string} deve vir preenchido com {string}") 
	public void o_campo_deve_vir_preenchido_com(String campo, String valor) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
		Assert.assertEquals(String.valueOf(contaMap.get(campo)), valor);
	}

	@Entao("o campo {string} deve vir preenchido com o valor do campo {string} reservado anteriormente") 
	public void o_campo_deve_vir_preenchido_com_o_valor_do_campo_reservado_anteriormente(String campo, String campoReservado) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
		Assert.assertEquals(String.valueOf(contaMap.get(campo)), dataMap.get(campoReservado));
	}

	@Entao("o campo {string} deve ser nulo") 
	public void o_campo_deve_ser_nulo(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertNull(contaMap.get(campo));
	}

	@Entao("o codigo de status retornado deve ser {int}")
	public void o_codigo_de_status_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}
	
	@Entao("deve ser retornado uma mensagem de erro {string}") 
	public void deve_ser_retornado_uma_mensagem_de_erro(String mensagem) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> errorMap = mapper.readValue(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}

	private <T> ResponseEntity<T> exchange(String path, Object request, Class<T> clazz){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);
		
		ResponseEntity<T> response = restTemplate.exchange("http://localhost:" + serverPort + path, 
				HttpMethod.POST, 
				httpEntity, 
				clazz);
		
		return response;
	}
	
}
