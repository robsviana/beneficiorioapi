# beneficiorioapi
# Aplicação Crud de Beneficiários de Plano de Saúde

Aplicação desenvolvida utilizando o framework Spring Boot, com o objetivo de manter o cadastro de beneficiários de um plano de saúde. Ela expõe uma API REST para criar, listar, atualizar e deletar informações de beneficiários.

## Funcionalidades

- Cadastro de novos beneficiários e seus documentos
- Cadastro de documentos de beneficiários existentes
- Listagem de todos beneficiários
- Atualização de informações de beneficiários
- Exclusão de beneficiários
- Consulta de beneficiários por ID
- Consulta Documentos cadastrados de beneficiários por ID

## Endpoints

Beneficiario

- `GET /v1/beneficiario/{id}`: Busca beneficiario por Id

- `PUT /v1/beneficiario/{id}`: Atualiza dados de um beneficiario

- `DELETE /v1/beneficiario/{id}`: Deleta um beneficiario

- `GET /v1/beneficiario`: Busca todos beneficiario na base de dados

- `POST /v1/beneficiario`: Realiza o cadastro de um beneficiario

- `POST /v1/beneficiario/documento`: Realiza o cadastro de beneficiario com documentos

- `GET /v1/beneficiario/documentos/{id}`: Busca documentos do beneficiario por Id

Documento

- `POST /v1/documento/{idBeneficiario}`: Realiza o cadastro de um Documento

Authentication Authorization API

- `POST /v1/auth/register`: Registra um usuário para acessar a aplicação

- `POST /v1/auth/login`: Realiza o login na aplicação para gerar tokens jwt de acessos

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação
- **Spring Boot**: Framework principal
    - **Spring Data JPA**: Para persistência e consultas no banco de dados
    - **Spring Web**: Para expor a API REST
    - **Spring Security**:  Para segurança de aplicações Java, oferece funcionalidades como autenticação e autorização com uso de tokens jwt

- **H2 Database**: Banco de dados em memória para testes
- **Maven**: Gerenciamento de dependências e build
- **Lombok**: Para reduzir boilerplate de código
- **Swagger**: Documentação da API



## Requisitos

- **Java 21** ou superior
- **Maven** 3.8+
- Banco de dados relacional H2 em memória

## Configurações

- Configuração de Database : Atualize o arquivo `application.properties` do H2 database com suas credenciais desejadas.

- Para trabalhar com banco de dados em formato de arquivo usar string de conexão: 
  - `spring.datasource.url=jdbc:h2:file:/data/beneficioariodb`

-  Para trabalhar com banco de dados em memória usar string de conexão:
   - `spring.datasource.url=jdbc:h2:mem:/data/beneficioariodb`

## Como Rodar a Aplicação

### Passo 1: Clonar o Repositório

git clone https://github.com/robsviana/beneficiorioapi.git

### Passo 2: Preparar o projeto para gerar o arquivo .jar,  executando os seguintes comandos Maven na pasta raiz da aplicação:
 
- 1 Limpar projeto de quaisquer builds anteriores.
    - mvn clean

- 2 Realizar a execução dos testes unitários.
  - mvn test

- 3 Construir o projeto e gerar o .jar
    - mvn package
  
- 4 Subir aplicação localmente
    - java -jar target/beneficiarioapi-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --server.port=8080

- OBS: ao subir aplicação as tabelas no banco de dados H2 serão criadas automaticamente devido a configuração "spring.jpa.hibernate.ddl-auto=update" presente no arquivo de propriedades.


## Gerar imagem Docker e subir aplicação no container
- 1 Realizar a instruções anteriores de como rodar a aplicação até o passo 2.3

- 4 Gerar imagem docker
  - docker build -t beneficiarioapi .

- 5 Identificar a imagem docker gerada
  - docker images

- 6 subir aplicação no container expondo para acessos na porta 8080
  - docker run -p 8080:8080 `IMAGEM ID`
  

## links 

- **H2 Database**: http://localhost:8080/h2-console/login.jsp

- **Swagger**: http://localhost:8080/swagger-ui/index.html#

## Contato
Connect with the project author on [LinkedIn](https://www.linkedin.com/in/robson-viana-freitas/) for any questions or suggestions.

