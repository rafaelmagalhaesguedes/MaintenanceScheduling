# Maintenance Scheduling

<div>
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="SpringBoot" src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white">
  <img alt="Docker" src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white">
</div>

## Descrição

Este projeto é um microsserviço desenvolvido para gerenciar o agendamento de manutenções em veículos. 
Ele oferece uma API RESTful que permite a criação, atualização, visualização e cancelamento de agendamentos, além de 
gerenciar informações de clientes e veículos associados. A autenticação e autorização são implementadas 
com base em roles (papéis) de usuário, como `MANAGER` e `CUSTOMER`, utilizando tokens JWT (JSON Web Tokens) para
garantir a segurança dos dados e controle de acesso.


## Principais Funcionalidades

- **Gerenciamento de Clientes**: Criação, atualização e visualização de informações de clientes.
- **Gerenciamento de Veículos**: Associação de veículos aos clientes, permitindo a criação e atualização de informações dos veículos.
- **Agendamento de Manutenções**: Criação, visualização, atualização e cancelamento de agendamentos de manutenções.
- **Autenticação e Autorização**: Controle de acesso baseado em roles de usuário (`MANAGER` e `CUSTOMER`), garantindo que apenas usuários autorizados possam acessar determinados endpoints.
- **Documentação da API**: Utilização do Swagger para fornecer uma documentação interativa e fácil de usar dos endpoints da API.
- **Testes Unitários**: Cobertura de testes para garantir a qualidade e a funcionalidade do código.

## Instruções de instalação

### Pré-requisitos

Para instalar e usar o projeto tenha em sua máquina os seguintes requisitos:

- Git
- Java 17
- Docker

### Etapas de instalação

1. Crie uma pasta e clone o projeto para sua máquina:

```bash
  mkdir microsservicoAgendamento/
  cd microsservicoAgendamento/
  git clone git@github.com:rafaelmagalhaesguedes/MicrosservicoAgendamento.git
```

2. Rodando o projeto com Docker:

Primeiro verifique se as portas 8080 (web service) e 5432 (postgres) não estão em uso em seu sistema.

Agora, suba os serviços docker com o comando abaixo

```bash
  docker compose up -d
```

## Instruções de uso

O serviço estará disponível na porta 8080 em seu localhost

1. Abra seu navegador ou API client e acesse http://localhost

## Tecnologias

- Java 17

- Spring Boot

- Postgres

- Docker

- Swagger (Para documentação da API)

- IntelliJ (IDE de desenvolvimento)

- Beekeeper Studio (Interface para gerenciar banco de dados)

- Insomnia Client (API Client)

## Padrões de Projeto

- **Builder:** utilizamos o padrão criacional builder para construir objetos de forma mais controlada.

- **Strategy:** utilizamos o padrão strategy para auxiliar na validação de dados sensíveis.

## Testes

O projeto possui testes unitários para garantir a qualidade e a funcionalidade do código.

Para rodar os testes use o comando no terminal na raiz do projeto:

```
mvn test
```

## Autenticação e Autorização

O projeto implementa autenticação de usuários com diferentes roles (papéis) para controlar o
acesso aos endpoints. As roles disponíveis são:

- __ADMIN:__ Administrador geral do sistema.
- __MANAGER:__ Gerente que pode gerenciar agendamentos e clientes.
- __CUSTOMER:__ Cliente que pode criar e visualizar seus próprios agendamentos e veículos.

## Documentação

O projeto utiliza o Swagger para documentação dos endpoints, acesse [neste link](http://localhost/swagger-ui/index.html#/).

## Proteção de dados

O projeto utiliza encriptação para os dados sensíveis da aplicação, para facilitar o processo utilizamos a biblioteca [JASYPT](https://mvnrepository.com/artifact/org.jasypt/jasypt).

## 📍 Principais Endpoints

Lista com os principais endpoints da aplicação

Autenticação:

| Rota                         | Descrição                                          
|------------------------------|-----------------------------------------------------
| <kbd>POST /auth/login</kbd>  | Autenticação no sistema
| <kbd>POST /auth/logout</kbd> | Sair do sistema


Customer:

| Rota                                                         | Descrição                                          
|--------------------------------------------------------------|-----------------------------------------------------
| <kbd>POST /customer</kbd>                                    | Cria um novo cliente
| <kbd>POST /vehicle/customer/{customerId}</kbd>               | Cria um veículo associado a um cliente
| <kbd>POST /schedule/customer/{customerId}</kbd>              | Cria um agendamento associado a um cliente
| <kbd>GET /schedule/customer/{customerId}</kbd>               | Lista os agendamento associados a um cliente
| <kbd>GET /customer/{customerId}</kbd>                        | Busca os dados de um cliente
| <kbd>PUT /schedule/{scheduleId}/customer/{customerId}/</kbd> | Cancela um agendamento associado a um cliente
| <kbd>PUT /address/customer/{customerId}</kbd>                | Atualiza um endereço associado a um cliente


Manager:

| Rota                                     | Descrição                                          
|------------------------------------------|-----------------------------------------------------
| <kbd>GET /schedule</kbd>                 | Lista todos os agendamentos
| <kbd>POST /order</kbd>                   | Cria ordem de serviço
| <kbd>PATCH /order/{orderId}</kbd>        | Finaliza ordem de serviço
| <kbd>POST /mechanic</kbd>                | Cria mecânico
| <kbd>GET /mechanic/{mechanicId}</kbd>    | Busca mecânico
| <kbd>PUT /mechanic/{mechanicId}</kbd>    | Atualiza dados do mecânico
| <kbd>DELETE /mechanic/{mechanicId}</kbd> | Deleta mecânico


Admin:

| Rota                                   | Descrição                                          
|----------------------------------------|-----------------------------------------------------
| <kbd>POST /manager</kbd>               | Cria gerente
| <kbd>GET /manager/{managerId}</kbd>    | Busca gerente
| <kbd>PUT /manager/{managerId}</kbd>    | Atualiza dados do gerente
| <kbd>DELETE /manager/{managerId}</kbd> | Deleta gerente


## Cria um cliente
#### POST /customer

**REQUEST**
```json
{
  "name": "Rafa Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "cep": "31510090"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "name": "Rafa Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "address": {
    "cep": "31510-090",
    "logradouro": "Rua Anita Garibaldi",
    "bairro": "Candelária",
    "localidade": "Belo Horizonte",
    "uf": "MG"
  }
}
```

## Cria um veículo associado a um cliente
#### POST /vehicle/customer/{customerId}

**REQUEST**
```json
{
  "licensePlate": "RWT9988",
  "model": "HB20",
  "make": "Hyundai",
  "year": 2022
}
```

**RESPONSE**
```json
{
  "id": 1,
  "licensePlate": "RWT9988",
  "model": "HB20",
  "manufacturer": "Hyundai",
  "year": 2022
}
```

## Cria um agendamento associado a um cliente
#### POST /schedule/customer/{customerId}

**REQUEST**
```json
{
  "dateSchedule": "2024-10-14T10:10:23.828952827",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática."
}
```

**RESPONSE**
```json
{
  "id": 1,
  "dateSchedule": "2024-10-14T10:10:23",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
  "scheduleStatus": "PENDENTE",
  "customer": {
    "id": 1,
    "name": "Rafa Guedes",
    "email": "rafa@email.com",
    "numberPhone": "31997785451",
    "vehicles": [
      {
        "model": "HB20",
        "licensePlate": "RWT9988"
      }
    ]
  }
}
```

## Lista todos os agendamentos
#### GET /schedule

**RESPONSE**
```json
[
  {
    "id": 1,
    "dateSchedule": "2024-10-14T10:10:23",
    "descriptionService": "Manutenção preventiva.",
    "scheduleStatus": "PENDENTE",
    "customer": {
      "id": 3,
      "name": "Rafa Guedes",
      "email": "rafael@email.com",
      "numberPhone": "31997785451",
      "vehicles": [
        {
          "model": "Ranger",
          "licensePlate": "RMT9987"
        }
      ]
    }
  },
  {
    "id": 2,
    "dateSchedule": "2024-10-14T14:10:23",
    "descriptionService": "Manutenção suspensão e freios.",
    "scheduleStatus": "PENDENTE",
    "customer": {
      "id": 3,
      "name": "Rafa Guedes",
      "email": "rafael@email.com",
      "numberPhone": "31997785451",
      "vehicles": [
        {
          "model": "Ranger",
          "licensePlate": "RMT9987"
        }
      ]
    }
  }
]
```

## Cria Ordem de Serviço
#### POST /order

**REQUEST**
```json
{
  "scheduleId": 1,
  "managerId": 2,
  "mechanicId": 1
}
```

**RESPONSE**
```json
{
  "id": 2,
  "descriptionService": "Manutenção preventiva.",
  "createdAt": "2024-10-07T14:23:48.976291738",
  "orderStatus": "IN_PROGRESS"
}
```

## Finaliza Ordem de Serviço
#### PATCH /order/{orderId}

**REQUEST**
```json
{
  "paymentType": "DEBIT_CARD",
  "totalAmount": "1000.00"
}
```

**RESPONSE**
```json
{
  "id": 2,
  "descriptionService": "Manutenção preventiva.",
  "paymentType": "DEBIT_CARD",
  "totalAmount": 1000.0,
  "createdAt": "2024-10-07T14:23:48.976292",
  "finishedAt": "2024-10-07T14:24:22.395491552",
  "orderStatus": "COMPLETED"
}
```

# Developed By

Rafa Guedes