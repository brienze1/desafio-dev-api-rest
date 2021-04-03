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
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class BloqueioDeContaTestSteps {

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private ContaRepository contaRepository;
	
	private ResponseEntity<String> responseString;
	private ResponseEntity<ContaDto> response;
	private TypeReference<Map<String, Object>> typeReference;
	private Map<String, String> dataMap;
	private HttpStatusCodeException e;
	
	@PostConstruct
	public void init() {
		typeReference = new TypeReference<Map<String, Object>>() {};
		
		dataMap = new HashMap<>();
	}
	
	@Dado("que uma pessoa foi cadastrada anteriormente no sistema com os dados abaixo") 
	public void que_uma_pessoa_foi_cadastrada_anteriormente_no_sistema_com_os_dados_abaixo(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);
		
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setCpf(pessoaMap.get("cpf"));
		pessoaDto.setDataNascimento(pessoaMap.get("data_nascimento"));
		pessoaDto.setNome(pessoaMap.get("nome"));
		
		responseString = exchange("/v1/pessoas", pessoaDto, HttpMethod.POST, String.class);
	}

	@Dado("que foi cadastrada a conta abaixo para o id_pessoa reservado anteriormente") 
	public void que_foi_cadastrada_a_conta_abaixo_para_o_id_pessoa_reservado_anteriormente(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);
		
		ContaDto contaDto = new ContaDto();
		contaDto.setLimiteSaqueDiario(BigDecimal.valueOf(Double.valueOf(contaMap.get("limite_saque_diario"))));
		contaDto.setTipoConta(Integer.valueOf(contaMap.get("tipo_conta")));
		contaDto.setIdPessoa(Long.valueOf(dataMap.get("id_pessoa")));
		
		responseString = exchange("/v1/contas", contaDto, HttpMethod.POST, String.class);
	}

	@Dado("que foi guardado o {string} que foi gerado") 
	public void que_foi_guardado_o_que_foi_gerado(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> pessoaMap = mapper.readValue(responseString.getBody(), typeReference);
		
		dataMap.put(campo, String.valueOf(pessoaMap.get(campo)));
	}

	@Dado("que o id_conta reservado ja tenha sido bloqueada anteriormente") 
	public void que_o_id_conta_reservado_ja_tenha_sido_bloqueada_anteriormente() {
		responseString = exchange("/v1/contas/bloqueios/" + dataMap.get("id_conta"), null, HttpMethod.PATCH, String.class);
	}

	@Dado("que o id_conta {int} nao tenha sido usado em nenhum cadastro") 
	public void que_o_id_conta_nao_tenha_sido_usado_em_nenhum_cadastro(Integer id_conta) {
		contaRepository.deleteAll();
	}
	
	@Quando("for solicitado o bloqueio do id_conta reservado") 
	public void for_solicitado_o_bloqueio_do_id_conta_reservado() {
		try {
			response = exchange("/v1/contas/bloqueios/" + dataMap.get("id_conta"), null, HttpMethod.PATCH, ContaDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}
	
	@Quando("for solicitado o bloqueio do id_conta {string}") 
	public void for_solicitado_o_bloqueio_do_id_conta(String idConta) {
		try {
			exchange("/v1/contas/bloqueios/" + idConta, null, HttpMethod.PATCH, String.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Entao("deve ser retornado os dados da conta bloqueada") 
	public void deve_ser_retornado_os_dados_da_conta_bloqueada(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);
		
		Assert.assertEquals(contaMap.get("limite_saque_diario"), response.getBody().getLimiteSaqueDiario().toString());
		Assert.assertEquals(contaMap.get("saldo"), response.getBody().getSaldo().toString());
		Assert.assertEquals(Integer.valueOf(contaMap.get("tipo_conta")), response.getBody().getTipoConta());
	}

	@Entao("o valor do campo {string} deve ser {string}") 
	public void o_valor_do_campo_deve_ser(String campo, String valor) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
		Assert.assertEquals(String.valueOf(contaMap.get(campo)), valor);
	}
	
	@Entao("deve ser retornado uma mensagem de erro com a frase {string}")
	public void deve_ser_retornado_uma_mensagem_de_erro_com_a_frase(String mensagem) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> errorMap = mapper.readValue(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
	}

	@Entao("o valor retornado do status ser {int}") 
	public void o_valor_retornado_do_status_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}
	
	private <T> ResponseEntity<T> exchange(String path, Object request, HttpMethod method, Class<T> clazz){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		
		HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);
		
		ResponseEntity<T> response = restTemplate.exchange("http://localhost:" + serverPort + path, 
				method, 
				httpEntity, 
				clazz);
		
		return response;
	}
	
}
