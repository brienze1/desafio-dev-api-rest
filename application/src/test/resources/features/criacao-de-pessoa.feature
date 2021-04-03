# language: pt
@CriacaoDePessoaTest
Funcionalidade: Teste de criacao de pessoa

	O sistema deve realizar a criacao da pessoa de forma correta seguindo as seguintes restricoes:
	1-) Deve ser enviado um nome.
	2-) Deve ser enviado um cpf de 11 caracteres.
	3-)	Deve ser enviada uma data de nascimento com o padrao dd/MM/yyyy.
	
	Cenario: Pessoa criada com sucesso
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado o cadastro completo da pessoa com os campos
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o campo "id_pessoa" devera vir preenchido
		E o status retornado deve ser 201

	Cenario: Pessoa nao criada erro de nome em branco
		Dado que exista a seguinte pessoa
		|	nome			|				|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "nome nao pode ser nulo ou em branco"
		E o status retornado deve ser 500

	Cenario: Pessoa nao criada erro de nome nulo
		Dado que exista a seguinte pessoa
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "nome nao pode ser nulo ou em branco"
		E o status retornado deve ser 500

	Cenario: Pessoa nao criada erro de cpf em branco
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe	|
		|	cpf				|				|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "cpf nao pode ser nulo ou em branco"
		E o status retornado deve ser 500

	Cenario: Pessoa nao criada erro de cpf em branco
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe	|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "cpf nao pode ser nulo ou em branco"
		E o status retornado deve ser 500
	
	Cenario: Pessoa nao criada erro de cpf com menos do que 11 caracteres
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe	|
		|	cpf				|	1234567891	|
		|	data_nascimento	|	25/01/1995	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "cpf deve conter 11 caracteres"
		E o status retornado deve ser 500
	
	Cenario: Pessoa nao criada erro de cpf com mais do que 11 caracteres
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe		|
		|	cpf				|	123456789123	|
		|	data_nascimento	|	25/01/1995		|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "cpf deve conter 11 caracteres"
		E o status retornado deve ser 500
	
	Cenario: Pessoa nao criada erro de data de nascimento em branco
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe		|
		|	cpf				|	12345678912	|
		|	data_nascimento	|					|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "data_nascimento nao pode ser nula ou em branco"
		E o status retornado deve ser 500
	
	Cenario: Pessoa nao criada erro de data de nascimento nula
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe		|
		|	cpf				|	12345678912	|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "data_nascimento nao pode ser nula ou em branco"
		E o status retornado deve ser 500
	
	Cenario: Pessoa nao criada erro de data de nascimento fora do padrao
		Dado que exista a seguinte pessoa
		|	nome			|	Jhon Doe		|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25-01-1995		|
		Quando for solicitada a criacao da pessoa
		Entao deve ser retornado a mensagem de erro "data deve ser informada no padrao dd/MM/yyyy"
		E o status retornado deve ser 500