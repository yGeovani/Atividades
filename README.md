# StockManager

Sistema web para gerenciamento de produtos e estoque, desenvolvido como atividade acadêmica.

## Tecnologias utilizadas

* Java 21
* Maven
* Jakarta Servlet
* Apache Tomcat 10
* MySQL
* JDBC
* HTML5
* CSS3
* JavaScript

## Estrutura do projeto

```text
GUIv2/
├── database/
│   └── banco.sql
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── stockmanager/
│       │           ├── model/
│       │           │   └── produto.java
│       │           ├── dao/
│       │           │   └── produtoDAO.java
│       │           ├── controller/
│       │           │   └── produtoServlet.java
│       │           └── util/
│       │               └── conexao.java
│       └── webapp/
│           ├── index.html
│           ├── JavaScrpt.js
│           └── style.css
├── pom.xml
└── README.md
```

## Banco de dados

O sistema utiliza o banco de dados MySQL `stock_manager`.

Para criar o banco e a tabela, execute o arquivo:

```text
database/banco.sql
```

## Configuração da conexão

A conexão com o banco de dados está localizada em:

```text
src/main/java/com/stockmanager/util/conexao.java
```

Configure o usuário e a senha do MySQL de acordo com o ambiente de execução.

## Como executar

### 1. Pré-requisitos

Instale:

* JDK 21
* Maven
* MySQL
* Apache Tomcat 10

### 2. Criar o banco

Execute o arquivo:

```text
database/banco.sql
```

### 3. Compilar o projeto

Na pasta raiz do projeto:

```bash
mvn clean package
```

O comando irá gerar:

```text
target/stockmanager.war
```

### 4. Executar no Tomcat

Copie o arquivo:

```text
target/stockmanager.war
```

para a pasta:

```text
webapps/
```

do Apache Tomcat.

Após iniciar o Tomcat, acesse:

```text
http://localhost:8080/stockmanager/
```

## Funcionalidades

* Cadastro de produtos
* Listagem de produtos
* Consulta de produtos
* Exclusão de produtos
* Validação de formulário
* Consulta automática de endereço através do CEP
* Persistência dos dados em banco MySQL
* Comunicação entre front-end e back-end através de requisições HTTP

## Arquitetura

O projeto utiliza uma organização baseada no padrão MVC:

* **Model:** representa os dados dos produtos.
* **DAO:** realiza a comunicação com o banco de dados através de JDBC.
* **Controller:** recebe as requisições HTTP e controla as operações.
* **View:** interface HTML, CSS e JavaScript utilizada pelo usuário.

## Autoria

Projeto desenvolvido para fins acadêmicos.
