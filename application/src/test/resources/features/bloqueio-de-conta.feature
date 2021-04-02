# language: pt
@BloqueioDeContaTest
Funcionalidade: Teste de bloqueio de conta

	O sistema deve realizar o bloqueio de conta de forma correta seguindo as seguintes restricoes:
	1-) Deve ser informado um id_conta valido.
	2-) A conta que vai ser bloqueada deve estar ativa.

	Cenario: Bloqueio de conta com sucesso
		Dado que uma pessoa foi cadastrada anteriormente no sistema com os dados abaixo
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E que foi guardado o "id_pessoa" que foi gerado 
		E que foi cadastrada a conta abaixo para o id_pessoa reservado anteriormente
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E que foi guardado o "id_conta" que foi gerado 
		Quando for solicitado o bloqueio do id_conta reservado
		Entao deve ser retornado os dados da conta bloqueada
		|	limite_saque_diario	|	100		|
		|	tipo_conta			|	1		|
		|	saldo				|	0.00	|
		E o valor do campo "flag_ativo" deve ser "false"
		E o valor retornado do status ser 200

	Cenario: Bloqueio de conta com erro conta nao esta ativa
		Dado que uma pessoa foi cadastrada anteriormente no sistema com os dados abaixo
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E que foi guardado o "id_pessoa" que foi gerado 
		E que foi cadastrada a conta abaixo para o id_pessoa reservado anteriormente
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E que foi guardado o "id_conta" que foi gerado 
		E que o id_conta reservado ja tenha sido bloqueada anteriormente
		Quando for solicitado o bloqueio do id_conta reservado
		Entao deve ser retornado uma mensagem de erro com a frase "id_conta ja esta bloqueado"
		E o valor retornado do status ser 500

	Cenario: Bloqueio de conta com erro conta nao existente
		Dado que o id_conta 101 nao tenha sido usado em nenhum cadastro
		Quando for solicitado o bloqueio do id_conta "101"
		Entao deve ser retornado uma mensagem de erro com a frase "id_conta nao encontrado"
		E o valor retornado do status ser 404
		
		