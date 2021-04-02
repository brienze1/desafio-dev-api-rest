Aplicação que expõe recursos em API Rest que realizam operações bancárias - Desafio Dock

Foi adotado o Clean Architechture como design pattern para o desenvolvimento devido a sua capacidade de separar as regras de negocio das outras dependencias necessarias para a inicializacao da api.

O projeto foi separado em 3 camadas:
   *  Application -> Configuracao dos beans do projeto e properties
   *  Delivery -> Camada exposta da API, dtos e controlers...
   *  Domain -> Camada de servico, regras de negocio etc...
   *  Integration -> Camada de integracao da API com banco de dados, chamadas Rest etc...

Alem disso a api conta tbm com rotas de healthCheck e metricas do Prometheus nas rotas abaixo:
http://localhost:8085/actuator/health
http://localhost:8085/actuator/prometheus

A documentação e testes da api, pode ser utilizado a rota do swagger abaixo:
http://localhost:8085/v2/api-docs
http://localhost:8085/swagger-ui.html

Passos concluidos de acordo com a ordem cronologica:

*  Criacao da casca do projeto (com spring boot)
*  Criacao de documentacao via swagger
*  Criacao dos actuators (health e prometheus)
*  Adicionar feature para teste integrado com cucumber
	- Feature criacao de conta
	- Feature criacao de pessoa
	- Feature deposito em conta
	- Feature consulta saldo
	- Feature saque de conta
	- Feature bloqueio de conta
	- Feature extrato de transacoes

Proximos passos:

*  Desenvolvimento do teste integrado com cucumber
*  Desenvolvimento dos testes unitarios para a camada de delivery
*  Desenvolvimento do controller
*  Desenvolvimento dos testes unitarios para a camada de domain
*  Desenvolvimento das regras de negocio
*  Desenvolvimento dos testes unitarios para a camada de integration
*  Modelagem da base de dados
*  Desenvolvimento das entidades e comunicacao com a base de dados
*  Refatoracao do codigo

Feito por Luis Brienze. 
Linkedin: https://www.linkedin.com/in/luisbrienze/
Link do repo: https://github.com/brienze1/desafio-dev-api-rest (privado)