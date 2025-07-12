# SergipePay 💸

### Status do Projeto: Finalizado ✔️

**SergipePay** é uma API RESTful que simula o backend de uma conta digital simples, desenvolvida como um projeto de portfólio para demonstrar habilidades em desenvolvimento backend com Java e o ecossistema Spring. O projeto abrange desde a criação e validação de clientes até a lógica de transações financeiras e testes unitários.

-----

## 📋 Funcionalidades Principais

  * ✅ **Gestão de Clientes:** Criação de novas contas para clientes.
  * ✅ **Validação de Dados:** Uso do Bean Validation para garantir a integridade dos dados de entrada.
  * ✅ **Segurança:** Hashing de senhas com BCrypt através do Spring Security.
  * ✅ **Operações Financeiras:** Endpoints para Depósito, Saque e Transferência entre contas.
  * ✅ **Consultas:** Endpoints para verificação de Saldo e Extrato de transações.
  * ✅ **Rastreabilidade:** Cada movimentação financeira gera um registro de transação para auditoria.
  * ✅ **Testes Unitários:** A camada de serviço possui testes com JUnit 5 e Mockito para garantir a qualidade da lógica de negócio.
  * ✅ **Documentação de API:** Geração automática da documentação com Springdoc (Swagger UI).

-----

## 🛠️ Tecnologias Utilizadas

A seguinte stack de tecnologias foi utilizada na construção do projeto:

  * **Java 17:** Versão LTS mais recente da linguagem.
  * **Spring Boot 3.3+:**
      * **Spring Web:** Para a criação dos controllers e endpoints REST.
      * **Spring Data JPA:** Para a persistência de dados e comunicação com o banco.
      * **Spring Security:** Para a implementação da segurança, focando no hashing de senhas.
      * **Spring Validation:** Para a validação declarativa dos DTOs.
  * **H2 Database:** Banco de dados em memória, ideal para desenvolvimento e testes.
  * **Maven:** Gerenciador de dependências e build do projeto.
  * **JUnit 5 & Mockito:** Para a escrita e execução de testes unitários na camada de serviço.
  * **Springdoc OpenAPI:** Para a geração automática e visualização da documentação da API.

-----

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a aplicação localmente.

### Pré-requisitos

  * JDK 17 ou superior instalado.
  * Maven 3.6 ou superior instalado.
  * Um cliente de API, como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/), para testar os endpoints.

### Passos

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/seu-usuario/sergipe-pay.git
    ```

2.  **Navegue até o diretório do projeto:**

    ```bash
    cd sergipe-pay
    ```

3.  **Execute a aplicação com o Maven:**

    ```bash
    mvn spring-boot:run
    ```

4.  **A aplicação estará disponível em `http://localhost:8080`.**

-----

## 📖 Acessando a Documentação e o Banco de Dados

Com a aplicação em execução, você pode acessar:

  * **Swagger UI (Documentação da API):**
    Abra seu navegador e acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    <br>Aqui você pode visualizar todos os endpoints e interagir com a API diretamente.

  * **H2 Console (Banco de Dados em Memória):**
    Abra seu navegador e acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    <br>Use as seguintes credenciais para conectar:

      * **JDBC URL:** `jdbc:h2:mem:testdb`
      * **User  Name:** `sa`
      * **Password:** (deixe em branco)

-----




## 📑 Endpoints da API

Aqui está uma lista dos principais endpoints disponíveis:

| Método | Endpoint                      | Descrição                                         | Corpo da Requisição (Exemplo)                                                                                                                                                                                                 |
| :----- | :---------------------------- | :-------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `POST` | `/api/clientes`                 | Cria um novo cliente e sua respectiva conta digital. | `{ "nome": "João Silva", "cpf": "11122233344", "email": "joao@email.com", "senha": "senhaForte123" }`                                                                                                                         |
| `POST` | `/api/transacao/depositar`      | Realiza um depósito em uma conta existente.         | `{ "agencia": 1, "numConta": 1234, "valor": 100.50 }`                                                                                                                                                                        |
| `POST` | `/api/transacao/sacar`          | Realiza um saque de uma conta existente.            | `{ "agencia": 1, "numConta": 1234, "valor": 50.00 }`                                                                                                                                                                         |
| `POST` | `/api/transacao/transferir`     | Transfere um valor de uma conta para outra.         | `{ "agenciaOrigem": 1, "numContaOrigem": 1234, "agenciaDestino": 1, "numContaDestino": 5678, "valor": 25.00 }`                                                                                                                 |
| `GET`  | `/api/contas/{numConta}/saldo`  | Consulta o saldo de uma conta específica.           | N/A                                                                                                                                                                                                                           |
| `GET`  | `/api/contas/{numConta}/extrato`| Retorna o extrato de transações de uma conta.     | N/A                                                                                                                                                                                                                           |
