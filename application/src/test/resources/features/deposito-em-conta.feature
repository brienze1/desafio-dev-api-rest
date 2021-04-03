# language: pt
@DepositoEmContaTest
Funcionalidade: Teste de deposito em conta

	O sistema deve realizar o deposito na conta de forma correta seguindo as seguintes restricoes:
	1-) Deve ser informado um id_conta valido.
	2-) A conta que vai receber o deposito deve estar ativa.
	3-) Deve ser informado um valor maior do que zero.
	4-)	A data da transacao deve ser preenchida automaticamente pelo sistema.
	5-) O sistema deve criar um id_transacao numerico automaticamente.
	
	Cenario: Deposito realizado com sucesso
		Dado que foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" que foi gerado tenha sido guardado
		E que foi cadastrada a conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado tenha sido guardado
		Quando for solicitado o "deposito" de 200.0 reais para o id_conta reservado
		Entao o dado "id_transacao" deve vir preenchido
		E o valor deve ser 200.00
		E o dado "data_transacao" deve vir preenchido
		E o dado "id_conta" deve vir preenchido com o valor "id_conta" reservado
		E o status da chamada retornado deve ser 202
	
	Cenario: Deposito nao realizado valor menor que zero
		Dado que foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" que foi gerado tenha sido guardado
		E que foi cadastrada a conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado tenha sido guardado
		Quando for solicitado o "deposito" de -200.0 reais para o id_conta reservado
		Entao deve ser retornado um erro com a seguinte mensagem "valor nao pode ser menor ou igual a zero"
		E o status da chamada retornado deve ser 500

	Cenario: Deposito nao realizado valor igual a zero
		Dado que foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" que foi gerado tenha sido guardado
		E que foi cadastrada a conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado tenha sido guardado
		Quando for solicitado o "deposito" de 0.0 reais para o id_conta reservado
		Entao deve ser retornado um erro com a seguinte mensagem "valor nao pode ser menor ou igual a zero"
		E o status da chamada retornado deve ser 500

	Cenario: Deposito nao realizado conta bloqueada
		Dado que foi cadastrada a seguinte pessoa anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" que foi gerado tenha sido guardado
		E que foi cadastrada a conta abaixo para o id_pessoa reservado
		|	limite_saque_diario	|	100	|
		|	tipo_conta			|	1	|
		E o "id_conta" que foi gerado tenha sido guardado
		E que tenha sido solicitado o bloqueio do id_conta reservado
		Quando for solicitado o "deposito" de 200.0 reais para o id_conta reservado
		Entao deve ser retornado um erro com a seguinte mensagem "deposito recusado, conta bloqueada"
		E o status da chamada retornado deve ser 500

	Cenario: Deposito nao realizado conta nao existente
		Dado que nao foi cadastrado nenhuma conta no sistema
		E que seja reservado o "id_conta" "101"
		Quando for solicitado o "deposito" de 200.0 reais para o id_conta reservado
		Entao deve ser retornado um erro com a seguinte mensagem "id_conta nao cadastrado no sistema"
		E o status da chamada retornado deve ser 404
