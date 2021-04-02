# language: pt
@CriacaoDeContaTest
Funcionalidade: Teste de criacao de conta

	O sistema deve realizar a criacao da conta de forma correta seguindo as seguintes restricoes:
	1-) Deve existir uma pessoa para criacao de uma conta.
	2-) O saldo na criacao deve ser 0.
	3-) O limite de saque diario nao pode ser menor ou igual a zero.
	4-) Caso nao seja especificado um limite o limite deve ser infinito.
	5-) Quando criada a conta deve ser ativa.
	6-) O tipo da conta deve ser informado na criacao da mesma, e deve ser um inteiro maior que zero.
	7-) A data da criacao deve ser definida automaticamente como a data atual.
	
	Cenario: Conta criada com sucesso
		Dado que tenha sido cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido guardado
		Quando for solicitada a criacao da conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		Entao deve ser retornado o cadastro completo da conta com os campos
		|	limite_saque_diario	|	100		|
		|	saldo				|	0.00	|
		|	tipo_conta			|	1		|
		E o campo "id_conta" deve vir preenchido
		E o campo "flag_ativo" deve vir preenchido com "true"
		E o campo "id_pessoa" deve vir preenchido com o valor do campo "id_pessoa" reservado anteriormente
		E o campo "data_criacao" deve vir preenchido
		E o codigo de status retornado deve ser 201
	
	Cenario: Conta criada com sucesso sem limite de saque
		Dado que tenha sido cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido guardado
		Quando for solicitada a criacao da conta abaixo para o id_pessoa reservado
		|	tipo_conta			|	1	|
		Entao deve ser retornado o cadastro completo da conta com os campos
		|	tipo_conta			|	1		|
		|	saldo				|	0.00	|
		E o campo "id_conta" deve vir preenchido
		E o campo "flag_ativo" deve vir preenchido com "true"
		E o campo "id_pessoa" deve vir preenchido com o valor do campo "id_pessoa" reservado anteriormente
		E o campo "data_criacao" deve vir preenchido
		E o campo "limite_saque_diario" deve ser nulo
		E o codigo de status retornado deve ser 201
	
	Cenario: Conta nao criada erro de pessoa nao existente
		Dado que nao exista nenhuma pessoa cadastrada para o idPessoa 101
		E o "id_pessoa" 101 tenha sido guardado
		Quando for solicitada a criacao da conta abaixo para o id_pessoa reservado
		|	tipo_conta			|	1		|
		|	limite_saque_diario	|	100	|
		Entao deve ser retornado uma mensagem de erro "id_pessoa nao cadastrado no sistema"
		E o codigo de status retornado deve ser 404
	
	Cenario: Conta nao criada limite de saque menor que o permitido
		Dado que tenha sido cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido guardado
		Quando for solicitada a criacao da conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	-100	|
		|	tipo_conta			|	1		|
		Entao deve ser retornado uma mensagem de erro "limite_saque_diario nao pode ser menor ou igual a zero"
		E o codigo de status retornado deve ser 500
	
	Cenario: Conta nao criada tipo da conta menor que zero
		Dado que tenha sido cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido guardado
		Quando for solicitada a criacao da conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	-1	|
		Entao deve ser retornado uma mensagem de erro "tipo_conta nao pode ser menor ou igual a zero"
		E o codigo de status retornado deve ser 500

