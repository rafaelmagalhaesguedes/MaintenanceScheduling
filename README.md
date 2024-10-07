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

1. Abra seu navegador ou API client e acesse http://localhost:8080/

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

O projeto utiliza o Swagger para documentação dos endpoints, acesse [neste link](http://localhost:8080/swagger-ui/index.html#/).

## Proteção de dados

O projeto utiliza encriptação para os dados sensíveis da aplicação, para facilitar o processo utilizamos a biblioteca [JASYPT](https://mvnrepository.com/artifact/org.jasypt/jasypt).

## 📍 Principais Endpoints

Lista com os principais endpoints da aplicação

| Rota                                                         | Descrição                                          
|--------------------------------------------------------------|-----------------------------------------------------
| <kbd>POST /customer</kbd>                                    | Cria um novo cliente
| <kbd>POST /auth/login</kbd>                     | Authentica o usuário no sistema
| <kbd>POST /vehicle/customer/{customerId}</kbd>               | Cria um veículo associado a um cliente
| <kbd>POST /schedule/customer/{customerId}</kbd>              | Cria um agendamento associado a um cliente
| <kbd>GET /schedule/customer/{customerId}</kbd>               | Lista os agendamento associados a um cliente
| <kbd>GET /customer/{customerId}</kbd>                        | Busca os dados de um cliente
| <kbd>PUT /schedule/{scheduleId}/customer/{customerId}/</kbd> | Cancela um agendamento associado a um cliente
| <kbd>PUT /address/customer/{customerId}</kbd>                | Atualiza um endereço associado a um cliente


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
## Lista todos os agendamentos associados a um cliente
#### GET /schedule/customer{customerId}

**RESPONSE**
```json
[
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
  },
  {
    "id": 2,
    "dateSchedule": "2024-10-13T10:00:00",
    "descriptionService": "Alinhamento, balancemanto e troca de pastilhas de freio.",
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
]
```

## Busca um cliente por ID
#### GET /customer/{customerId}

**RESPONSE**
```json
{
  "id": 1,
  "name": "Rafael Guedes",
  "email": "rafa@email.com",
  "numberPhone": "31997785451",
  "document": "12345678910",
  "address": {
    "cep": "31510-090",
    "logradouro": "Rua Anita Garibaldi",
    "bairro": "Candelária",
    "localidade": "Belo Horizonte",
    "uf": "MG"
  },
  "vehicles": [
    {
      "id": 1,
      "licensePlate": "RWT9988",
      "model": "HB20",
      "make": "Hyundai",
      "year": 2022
    }
  ],
  "schedules": [
    {
      "id": 1,
      "dateSchedule": "2024-10-14T10:10:23",
      "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
      "scheduleStatus": "PENDENTE"
    },
    {
      "id": 2,
      "dateSchedule": "2024-10-14T10:10:00",
      "descriptionService": "Alinhamento, balancemanto e troca de pastilhas de freio.",
      "scheduleStatus": "PENDENTE"
    }
  ]
}
```

## Atualiza um endereço associado a um cliente
#### PUT /address/customer{customerId}

**REQUEST**
```json
{
  "cep": "45810-000",
  "logradouro": "Rua Carlos C Silva",
  "bairro": "Parque Ecológico",
  "localidade": "Porto Seguro",
  "uf": "BA"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "cep": "45810-000",
  "logradouro": "Rua Carlos C Silva",
  "bairro": "Parque Ecológico",
  "localidade": "Porto Seguro",
  "uf": "BA"
}
```

## Atualiza o Status de um agendamento
#### PUT /schedule/{scheduleId}

**REQUEST**
```json
{
  "scheduleStatus": "REALIZADO"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "dateSchedule": "2024-10-14T10:10:23",
  "descriptionService": "Manutenção preventiva e substituição da bomba d'água e válvula termostática.",
  "scheduleStatus": "REALIZADO",
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

# Developed By

Rafa Guedes