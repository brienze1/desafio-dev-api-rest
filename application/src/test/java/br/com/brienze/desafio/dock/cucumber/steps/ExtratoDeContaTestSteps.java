package br.com.brienze.desafio.dock.cucumber.steps;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import br.com.brienze.desafio.dock.entity.TransacaoEntity;
import br.com.brienze.desafio.dock.repository.TransacaoRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import io.cucumber.datatable.DataTable;

public class ExtratoDeContaTestSteps {

	@LocalServerPort
	private int serverPort;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TransacaoRepository transacaoRepository;

	private ResponseEntity<String> responseString;
	private ResponseEntity<TransacaoDto> responseTransacao;
	private ResponseEntity<String> response;
	private TypeReference<Map<String, Object>> typeReference;
	private TypeReference<List<TransacaoDto>> typeReferenceExtrato;
	private Map<String, String> dataMap;
	private HttpStatusCodeException e;
	private DateTimeFormatter formatter;
	
	@PostConstruct
	public void init() {
		typeReference = new TypeReference<Map<String, Object>>() {};

		typeReferenceExtrato = new TypeReference<List<TransacaoDto>>() {};
		
		dataMap = new HashMap<>();
		
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	}

	@Dado("que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema")
	public void que_a_pessoa_dos_dados_abaixo_foi_cadastrada_anteriormente_no_sistema(DataTable dataTable) {
		Map<String, String> pessoaMap = dataTable.asMap(String.class, String.class);

		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setCpf(pessoaMap.get("cpf"));
		pessoaDto.setDataNascimento(pessoaMap.get("data_nascimento"));
		pessoaDto.setNome(pessoaMap.get("nome"));

		responseString = exchange("/v1/pessoas", pessoaDto, HttpMethod.POST, String.class);
	}

	@Dado("o {string} gerado tenha sido armazenado")
	public void o_gerado_tenha_sido_armazenado(String campo) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> pessoaMap = mapper.readValue(mapper.writeValueAsString(responseString.getBody()), typeReference);

		dataMap.put(campo, String.valueOf(pessoaMap.get(campo)));
	}

	@Dado("que foi cadastrada a conta abaixo para o id_pessoa armazenado")
	public void que_foi_cadastrada_a_conta_abaixo_para_o_id_pessoa_armazenado(DataTable dataTable) {
		Map<String, String> contaMap = dataTable.asMap(String.class, String.class);

		ContaDto contaDto = new ContaDto();
		contaDto.setLimiteSaqueDiario(BigDecimal.valueOf(Double.valueOf(contaMap.get("limite_saque_diario"))));
		contaDto.setTipoConta(Integer.valueOf(contaMap.get("tipo_conta")));
		contaDto.setIdPessoa(Long.valueOf(dataMap.get("id_pessoa")));

		responseString = exchange("/v1/contas", contaDto, HttpMethod.POST, String.class);
	}

	@Dado("que tenham sido feitas as seguintes transacoes no id_conta armazenado")
	public void que_tenham_sido_feitas_as_seguintes_transacoes_no_id_conta_armazenado(DataTable dataTable) {
		List<Map<String, String>> transacaoMaps = dataTable.asMaps(String.class, String.class);
		
		for (Map<String, String> transacaoMap : transacaoMaps) {
			for(int i=0; i<Integer.valueOf(transacaoMap.get("quantidade")); i++) {
				TransacaoDto transacaoDto = new TransacaoDto();
				transacaoDto.setValor(BigDecimal.valueOf(Double.valueOf(transacaoMap.get("valor"))));
				transacaoDto.setIdConta(Long.valueOf(dataMap.get("id_conta")));
				
				responseTransacao = exchange("/v1/contas/" + transacaoMap.get("tipo_transacao") + "s", null, HttpMethod.POST, TransacaoDto.class);
				
				Optional<TransacaoEntity> transacaoEntity = transacaoRepository.findById(responseTransacao.getBody().getIdTransacao());
				
				LocalDateTime data = LocalDateTime.parse(transacaoMap.get("data"), formatter);
				LocalDateTime dataTransacao = LocalDateTime.of(data.getYear(), 
						data.getMonth(), 
						data.getDayOfMonth(), 
						transacaoEntity.get().getDataTransacao().getHour(),
						transacaoEntity.get().getDataTransacao().getMinute(),
						transacaoEntity.get().getDataTransacao().getSecond(),
						transacaoEntity.get().getDataTransacao().getNano());
				
				transacaoEntity.get().setDataTransacao(dataTransacao);
				transacaoRepository.save(transacaoEntity.get());
			}
		}
	}

	@Dado("que foi reservado o {string} {int}")
	public void que_foi_reservado_o(String campo, Integer valor) {
		dataMap.put(campo, String.valueOf(valor));
	}

	@Quando("for solicitado o extrato do id_conta reservado com os parametros abaixo")
	public void for_solicitado_o_extrato_do_id_conta_reservado_com_os_parametros_abaixo(DataTable dataTable) {
		try {
			response = exchange("/v1/contas/" + dataMap.get("id_conta"), null, HttpMethod.GET, String.class);
			
			Assert.assertNotNull(response);
		} catch(HttpStatusCodeException ex) {
			e = ex;
		}
	}

	@Entao("deve ser retornado uma lista com as transacoes")
	public void deve_ser_retornado_uma_lista_com_as_transacoes(DataTable dataTable) throws JsonMappingException, JsonProcessingException {
		List<TransacaoDto> transacoesDto = mapper.readValue(response.getBody(), typeReferenceExtrato);
		List<Map<String, String>> transacaoMaps = dataTable.asMaps(String.class, String.class);
		
		
		for (Map<String, String> transacaoMap : transacaoMaps) {
			int contador = 0;
			for (TransacaoDto transacaoDto : transacoesDto) {
				boolean valid = false;
				
				LocalDateTime data = LocalDateTime.parse(transacaoMap.get("data"));
				if(transacaoDto.getDataTransacao().getDayOfMonth() != data.getDayOfMonth()) {
					valid = false;
				}
				if(transacaoDto.getDataTransacao().getMonthValue() != data.getMonthValue()) {
					valid = false;
				}
				if(transacaoDto.getDataTransacao().getYear() != data.getYear()) {
					valid = false;
				}
				if(transacaoDto.getValor().doubleValue() != Double.valueOf(transacaoMap.get("valor"))) {
					valid = false;
				}
				if(valid) {
					contador++;
				}
			}
			Assert.assertEquals(transacaoMap.get("quantidade"), contador);
		}
	}

	@Entao("o id_conta das contas deve vir preenchido com o valor id_conta armazenado")
	public void o_id_conta_das_contas_deve_vir_preenchido_com_o_valor_id_conta_armazenado() throws JsonMappingException, JsonProcessingException {
		List<TransacaoDto> transacoesDto = mapper.readValue(response.getBody(), typeReferenceExtrato);
		
		for (TransacaoDto transacaoDto : transacoesDto) {
			Assert.assertEquals(dataMap.get("id_conta"), transacaoDto.getIdConta());
		}
	}

	@Entao("o status da chamada de extrato retornado deve ser {int}")
	public void o_status_da_chamada_de_extrato_retornado_deve_ser(Integer statusCode) {
		if (e != null) {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(e.getRawStatusCode()));
		} else {
			Assert.assertEquals(String.valueOf(statusCode), String.valueOf(response.getStatusCodeValue()));
		}
	}

	@Entao("deve ser retornado uma mensagem de erro com o seguinte texto {string}")
	public void deve_ser_retornado_uma_mensagem_de_erro_com_o_seguinte_texto(String mensagem)
			throws JsonMappingException, JsonProcessingException {
		Map<String, Object> errorMap = mapper.readValue(e.getResponseBodyAsString(), typeReference);

		Assert.assertEquals(mensagem, errorMap.get("message"));
	}

	private <T> ResponseEntity<T> exchange(String path, Object request, HttpMethod method, Class<T> clazz) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<T> response = restTemplate.exchange("http://localhost:" + serverPort + path, method, httpEntity,
				clazz);

		return response;
	}
}
