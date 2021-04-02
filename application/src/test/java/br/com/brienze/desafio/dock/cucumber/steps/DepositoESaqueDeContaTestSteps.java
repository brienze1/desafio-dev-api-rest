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
import br.com.brienze.desafio.dock.dto.TransacaoDto;
import br.com.brienze.desafio.dock.repository.ContaRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class DepositoESaqueDeContaTestSteps {

	@LocalServerPort
	private int serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ContaRepository contaRepository;

	private ResponseEntity<String> responseString;
	private ResponseEntity<TransacaoDto> response;
	private TypeReference<Map<String, Object>> typeReference;
	private Map<String, String> dataMap;
	private HttpStatusCodeException e;
	
	@PostConstruct
	public void init() {
		typeReference = new TypeReference<Map<String, Object>>() {};
		
		dataMap = new HashMap<>();
	}
	
	@Dado("que foi cadastrada a seguinte pessoa anteriormente no sistema") 
	public void que_foi_cadastrada_a_seguinte_pessoa_anteriormente_no_sistema(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);
		
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setCpf(pessoaMap.get("cpf"));
		pessoaDto.setDataNascimento(pessoaMap.get("data_nascimento"));
		pessoaDto.setNome(pessoaMap.get("nome"));
		
		responseString = exchange("/v1/pessoas", pessoaDto, HttpMethod.POST, String.class);
	}

	@Dado("o {string} que foi gerado tenha sido guardado") 
	public void o_que_foi_gerado_tenha_sido_guardado(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> pessoaMap = mapper.readValue(mapper.writeValueAsString(responseString.getBody()), typeReference);
		
		dataMap.put(campo, String.valueOf(pessoaMap.get(campo)));
	}

	@Dado("que foi cadastrada a conta abaixo para o id_pessoa reservado") 
	public void que_foi_cadastrada_a_conta_abaixo_para_o_id_pessoa_reservado(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);
		
		ContaDto contaDto = new ContaDto();
		contaDto.setLimiteSaqueDiario(BigDecimal.valueOf(Double.valueOf(contaMap.get("limite_saque_diario"))));
		contaDto.setTipoConta(Integer.valueOf(contaMap.get("tipo_conta")));
		contaDto.setIdPessoa(Long.valueOf(dataMap.get("id_pessoa")));
		
		responseString = exchange("/v1/contas", contaDto, HttpMethod.POST, String.class);
	}

	@Dado("que tenha sido solicitado o bloqueio do id_conta reservado") 
	public void que_tenha_sido_solicitado_o_bloqueio_do_id_conta_reservado() {
		responseString = exchange("/v1/contas/bloqueios/" + dataMap.get("id_conta"), null, HttpMethod.PATCH, String.class);
	}

	@Dado("que nao foi cadastrado nenhuma conta no sistema") 
	public void que_nao_foi_cadastrado_nenhuma_conta_no_sistema() {
		contaRepository.deleteAll();
	}

	@Dado("que seja reservado o {string} {string}") 
	public void que_seja_reservado_o(String campo, String valor) {
		dataMap.put(campo, valor);
	}
	
	@Quando("for solicitado o {string} de {double} reais para o id_conta reservado") 
	public void for_solicitado_o_de_reais_para_o_id_conta_reservado(String path, Double valor) {
		TransacaoDto transacaoDto = new TransacaoDto();
		transacaoDto.setValor(BigDecimal.valueOf(valor));
		transacaoDto.setIdConta(Long.valueOf(dataMap.get("id_conta")));
		
		try {
			response = exchange("/v1/contas/" + path + "s", null, HttpMethod.POST, TransacaoDto.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}
	
	@Entao("o dado {string} deve vir preenchido")
	public void o_dado_deve_vir_preenchido(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
	}

	@Entao("o valor deve ser {double}")
	public void o_valor_deve_ser(Double valor) {
		Assert.assertEquals(BigDecimal.valueOf(valor), response.getBody().getValor());
	}

	@Entao("o dado {string} deve vir preenchido com o valor {string} reservado") 
	public void o_dado_deve_vir_preenchido_com_o_valor_reservado(String campo, String campoReservado) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> contaMap = mapper.readValue(mapper.writeValueAsString(response.getBody()), typeReference);
		   
		Assert.assertTrue(contaMap.containsKey(campo));
		Assert.assertFalse(String.valueOf(contaMap.get(campo)).isBlank());
		Assert.assertEquals(String.valueOf(contaMap.get(campo)), dataMap.get(campoReservado));
	}

	@Entao("o status da chamada retornado deve ser {int}") 
	public void o_status_da_chamada_retornado_deve_ser(Integer statusCode) {
		if(e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}

	@Entao("deve ser retornado um erro com a seguinte mensagem {string}") 
	public void deve_ser_retornado_um_erro_com_a_seguinte_mensagem(String mensagem) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> errorMap = mapper.readValue(e.getResponseBodyAsString(), typeReference);
		
		Assert.assertEquals(mensagem, errorMap.get("message"));
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
