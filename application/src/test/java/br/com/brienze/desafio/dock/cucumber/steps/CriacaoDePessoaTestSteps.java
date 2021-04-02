package br.com.brienze.desafio.dock.cucumber.steps;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brienze.desafio.dock.Application;
import br.com.brienze.desafio.dock.dto.PessoaDto;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriacaoDePessoaTestSteps {

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	private PessoaDto pessoaDto;
	private ResponseEntity<PessoaDto> response;
	private TypeReference<Map<String, Object>> typeReference;
	private HttpStatusCodeException e;

	@PostConstruct
	public void init() {
		pessoaDto = new PessoaDto();
		
		typeReference = new TypeReference<Map<String, Object>>() {};
	}
	
	@Dado("que exista a seguinte pessoa") 
	public void que_exista_a_seguinte_pessoa(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);
		
		pessoaDto.setCpf(pessoaMap.get("cpf"));
		pessoaDto.setDataNascimento(pessoaMap.get("data_nascimento"));
		pessoaDto.setNome(pessoaMap.get("nome"));
	}

	@Quando("for solicitada a criacao da pessoa") 
	public void for_solicitada_a_criacao_da_pessoa() {
		try {
			response = exchange("/v1/pessoas", pessoaDto, PessoaDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Entao("deve ser retornado o cadastro completo da pessoa com os campos") 
	public void deve_ser_retornado_o_cadastro_completo_da_pessoa_com_os_campos(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);
		
		Assert.assertEquals(pessoaMap.get("nome"), response.getBody().getNome());
		Assert.assertEquals(pessoaMap.get("cpf"), response.getBody().getCpf());
		Assert.assertEquals(pessoaMap.get("data_nascimento"), response.getBody().getDataNascimento());
	}

	@Entao("o campo {string} devera vir preenchido") 
	public void o_campo_devera_vir_preenchido(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> pessoaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
	   
		Assert.assertTrue(pessoaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(pessoaMap.get(campo)).isBlank());
	}

	@Entao("o status retornado deve ser {int}") 
	public void o_status_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}

	@Entao("deve ser retornado a mensagem de erro {string}") 
	public void deve_ser_retornado_a_mensagem_de_erro(String mensagem) throws JsonMappingException, JsonProcessingException {
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
