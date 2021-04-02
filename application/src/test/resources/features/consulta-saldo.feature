# language: pt
@ConsultaSaldoTest
Funcionalidade: Teste de consulta saldo de conta

	O sistema deve realizar o deposito na conta de forma correta seguindo as seguintes restricoes:
	1-) Deve ser informado um id_conta valido.
	2-) Deve ser permitido a consulta de saldo mesmo se a conta estiver bloqueada.
	
	Cenario: Consulta saldo realizado com sucesso
		Dado que ja foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E que o "id_pessoa" que foi gerado tenha sido guardado
		E foi cadastrada a conta abaixo para o "id_pessoa" reservado anteriormente
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado anteriormente tenha sido guardado
		Quando for solicitado a consulta de saldo para o "id_conta" reservado
		Entao devem ser retornados os dados da conta com os campos
		|	limite_saque_diario	|	100		|
		|	tipo_conta			|	1		|
		|	saldo				|	0.00	|
		|	tipo_conta			|	1		|
		E o valor do campo "id_conta" deve vir preenchido
		E o valor do campo "flag_ativo" deve vir preenchido com "true"
		E o valor do campo "id_pessoa" deve vir preenchido com o valor do campo "id_pessoa" reservado anteriormente
		E o valor do campo "data_criacao" deve vir preenchido
		E o status retornado deve ser 200
	
	Cenario: Consulta saldo realizado com sucesso apos deposito
		Dado que ja foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E que o "id_pessoa" que foi gerado tenha sido guardado
		E foi cadastrada a conta abaixo para o "id_pessoa" reservado anteriormente
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado anteriormente tenha sido guardado
		E que foi solicitado o deposito de 200 reais para o "id_conta" reservado
		Quando for solicitado a consulta de saldo para o "id_conta" reservado
		Entao devem ser retornados os dados da conta com os campos
		|	limite_saque_diario	|	100		|
		|	tipo_conta			|	1		|
		|	saldo				|	200.00	|
		|	tipo_conta			|	1		|
		E o valor do campo "id_conta" deve vir preenchido
		E o valor do campo "flag_ativo" deve vir preenchido com "true"
		E o valor do campo "id_pessoa" deve vir preenchido com o valor do campo "id_pessoa" reservado anteriormente
		E o valor do campo "data_criacao" deve vir preenchido
		E o status retornado deve ser 200
	
	Cenario: Consulta saldo realizado com sucesso conta bloqueada
		Dado que ja foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E que o "id_pessoa" que foi gerado tenha sido guardado
		E foi cadastrada a conta abaixo para o "id_pessoa" reservado anteriormente
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado anteriormente tenha sido guardado
		E que foi solicitado o deposito de 200 reais para o "id_conta" reservado
		E que foi solicitado o bloqueio do "id_conta" reservado
		Quando for solicitado a consulta de saldo para o "id_conta" reservado
		Entao devem ser retornados os dados da conta com os campos
		|	limite_saque_diario	|	100		|
		|	tipo_conta			|	1		|
		|	saldo				|	200.00	|
		|	tipo_conta			|	1		|
		E o valor do campo "id_conta" deve vir preenchido
		E o valor do campo "flag_ativo" deve vir preenchido com "false"
		E o valor do campo "id_pessoa" deve vir preenchido com o valor do campo "id_pessoa" reservado anteriormente
		E o valor do campo "data_criacao" deve vir preenchido
		E o status retornado deve ser 200
	
	Cenario: Consulta saldo nao realizado conta nao existente
		Dado que nao exista uma conta com o id 101 criada
		E que o "id_conta" 101 seja reservado
		Quando for solicitado a consulta de saldo para o "id_conta" reservado
		Entao deve ser retornado um excessao com a mensagem "id_conta nao encontrado"
		E o status retornado deve ser 404
