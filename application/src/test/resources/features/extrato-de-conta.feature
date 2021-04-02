# language: pt
@ExtratoDeContaTest
Funcionalidade: Teste de extrado de conta

	O sistema deve devolver o extrato na conta de forma correta seguindo as seguintes restricoes:
	1-) Deve ser informado um id_conta valido.
	2-) Caso nao seja solicitado o extrato sem passar nenhum parametro devem ser retornados as ultimas 30 transacoes cadastradas.
	3-) Caso seja passado algum parametro (data inicio, data fim, quantidade e pagina) devem ser retornados no maximo 30 valores, por pagina comecando pelos mais recentes.
	4-) A data inicio deve ser anterior a data fim.
	5-) A data inicio e a data fim devem ser enviadas no formato dd/MM/yyyy.
	6-) A quantidade deve ser maior que 0
	7-) a pagina deve ser maior ou igual a 0
	
	Cenario: Extrato retornado com sucesso
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta		|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|		|
		|	data_fim	|		|
		|	quantidade	|		|
		|	pagina		|		|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	500		|	1			| 19/07/2020	|
		|	1000	|	2			| 30/12/2020	|
		|	-100	|	4			| 05/01/2021	|
		|	-200	|	5			| 25/01/2021	|
		|	-300	|	6			| 10/02/2021	|
		|	-400	|	3			| 17/02/2021	|
		|	-500	|	7			| 20/03/2021	|
		|	-1000	|	2			| 01/04/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta		|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|		|
		|	data_fim	|		|
		|	quantidade	|	15	|
		|	pagina		|		|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	-300	|	3			| 10/02/2021	|
		|	-400	|	3			| 17/02/2021	|
		|	-500	|	7			| 20/03/2021	|
		|	-1000	|	2			| 01/04/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade e pagina
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|		|
		|	data_fim	|		|
		|	quantidade	|	15	|
		|	pagina		|	1	|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	500		|	1			| 19/07/2020	|
		|	1000	|	2			| 30/12/2020	|
		|	-100	|	4			| 05/01/2021	|
		|	-200	|	5			| 25/01/2021	|
		|	-300	|	3			| 10/02/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade e data inicio
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	17/02/2021	|
		|	data_fim	|				|
		|	quantidade	|	15			|
		|	pagina		|				|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	-400	|	3			| 17/02/2021	|
		|	-500	|	7			| 20/03/2021	|
		|	-1000	|	2			| 01/04/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade e data fim
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|				|
		|	data_fim	|	17/02/2021	|
		|	quantidade	|	15			|
		|	pagina		|				|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	-100	|	1			| 05/01/2021	|
		|	-200	|	5			| 25/01/2021	|
		|	-300	|	6			| 10/02/2021	|
		|	-400	|	3			| 17/02/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade pagina e data incio
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	05/01/2021	|
		|	data_fim	|				|
		|	quantidade	|	15			|
		|	pagina		|	1			|
		Entao deve ser retornado uma lista com as transacoes
		|	valor		|	quantidade	| data 			|
		|	-100		|	4			| 05/01/2021	|
		|	-200		|	5			| 25/01/2021	|
		|	-300		|	3			| 10/02/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade data incio e data fim
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	05/01/2021	|
		|	data_fim	|	25/01/2021	|
		|	quantidade	|	6			|
		|	pagina		|				|
		Entao deve ser retornado uma lista com as transacoes
		|	valor		|	quantidade	| data 			|
		|	-100		|	1			| 05/01/2021	|
		|	-200		|	5			| 25/01/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por data incio e data fim
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	05/01/2021	|
		|	data_fim	|	25/01/2021	|
		|	quantidade	|				|
		|	pagina		|				|
		Entao deve ser retornado uma lista com as transacoes
		|	valor		|	quantidade	| data 			|
		|	-100		|	4			| 05/01/2021	|
		|	-200		|	5			| 25/01/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato retornado com sucesso por quantidade pagina data incio e data fim
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	05/01/2021	|
		|	data_fim	|	25/01/2021	|
		|	quantidade	|	6			|
		|	pagina		|	1			|
		Entao deve ser retornado uma lista com as transacoes
		|	valor	|	quantidade	| data 			|
		|	-100	|	3			| 05/01/2021	|
		E o id_conta das contas deve vir preenchido com o valor id_conta armazenado
		E o status da chamada de extrato retornado deve ser 200
	
	Cenario: Extrato nao retornado id conta invalido
		Dado que foi reservado o "id_conta" 101
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|				|
		|	data_fim	|				|
		|	quantidade	|				|
		|	pagina		|				|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "id_conta nao encontrado"
		E o status da chamada de extrato retornado deve ser 404
	
	Cenario: Extrato nao retornado data inicio depois da data fim
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	25/01/2021	|
		|	data_fim	|	05/01/2021	|
		|	quantidade	|				|
		|	pagina		|				|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "data_inicio deve vir antes da data_fim"
		E o status da chamada de extrato retornado deve ser 500
	
	Cenario: Extrato nao retornado data inicio fora do padrao
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|	25-01-2021	|
		|	data_fim	|				|
		|	quantidade	|				|
		|	pagina		|				|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "data_incio deve ser informada no padrao dd/MM/yyyy"
		E o status da chamada de extrato retornado deve ser 500
	
	Cenario: Extrato nao retornado data fim fora do padrao
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|				|
		|	data_fim	|	25-01-2021	|
		|	quantidade	|				|
		|	pagina		|				|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "data_fim deve ser informada no padrao dd/MM/yyyy"
		E o status da chamada de extrato retornado deve ser 500
	
	Cenario: Extrato nao retornado quantidade menor que zero
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|		|
		|	data_fim	|		|
		|	quantidade	|	-10	|
		|	pagina		|		|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "quantidade deve ser maior ou igual a zero"
		E o status da chamada de extrato retornado deve ser 500
	
	Cenario: Extrato nao retornado pagina menor que zero
		Dado que a pessoa dos dados abaixo foi cadastrada anteriormente no sistema
		|	nome			|	Jhon Doe	|
		|	cpf				|	12345678912	|
		|	data_nascimento	|	25/01/1995	|
		E o "id_pessoa" gerado tenha sido armazenado
		E que foi cadastrada a conta abaixo para o id_pessoa armazenado
		|	tipo_conta			|	1	|
		E o "id_conta" gerado tenha sido armazenado
		E que tenham sido feitas as seguintes transacoes no id_conta armazenado
		|	tipo_transacao	|	valor	|	quantidade	| data 			|
		|	deposito		|	100		|	4			| 25/01/2020	|
		|	deposito		|	200		|	5			| 25/02/2020	|
		|	deposito		|	300		|	6			| 25/03/2020	|
		|	deposito		|	400		|	3			| 14/01/2020	|
		|	deposito		|	500		|	7			| 19/07/2020	|
		|	deposito		|	1000	|	2			| 30/12/2020	|
		|	saque			|	100		|	4			| 05/01/2021	|
		|	saque			|	200		|	5			| 25/01/2021	|
		|	saque			|	300		|	6			| 10/02/2021	|
		|	saque			|	400		|	3			| 17/02/2021	|
		|	saque			|	500		|	7			| 20/03/2021	|
		|	saque			|	1000	|	2			| 01/04/2021	|
		Quando for solicitado o extrato do id_conta reservado com os parametros abaixo
		|	data_inicio	|		|
		|	data_fim	|		|
		|	quantidade	|		|
		|	pagina		|	-1	|
		Entao deve ser retornado uma mensagem de erro com o seguinte texto "pagina deve ser maior ou igual a zero"
		E o status da chamada de extrato retornado deve ser 500