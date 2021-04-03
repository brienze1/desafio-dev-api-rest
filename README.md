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

Para a base de dados foi utilizado o H2 (base em memoria utilizado para desenvolvimento local). 
O script de criacao da base esta localizado no serguinte arquivo: ./appliction/src/main/resources/data.sql
Com a aplicacao rodando é possivel consultar, alterar e deletar o conteudo da base na rota abaixo utilizando comandnos em SQL:
http://localhost:8085/h2-console

O dockerfile para gerar a imagem dockerizada foi criado e esta na pasta principal do projeto, a imagem gerada pode ser visualizada neste link:
https://hub.docker.com/repository/docker/lfbrienze/desafio

Foi feito o desenho de uma utilizacao da aws para implementar a api, o desenho foi feito com a utilizacao do draw.io e se encontra na pasta principal do projeto.

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
*  Desenvolvimento do teste integrado com cucumber
*  Modelagem da base de dados
*  Desenvolvimento das entidades e comunicacao com a base de dados
*  Desenvolvimento dos testes unitarios para a camada de delivery
*  Desenvolvimento do controller
*  Desenvolvimento dos testes unitarios para a camada de domain
*  Desenvolvimento das regras de negocio
*  Desenvolvimento dos testes unitarios para a camada de integration
*  Criar Dockerfile

Proximos passos:

*  Refatoracao do codigo

Feito por Luis Brienze. 
Linkedin: https://www.linkedin.com/in/luisbrienze/
Link do repo: https://github.com/brienze1/desafio-dev-api-rest (privado)